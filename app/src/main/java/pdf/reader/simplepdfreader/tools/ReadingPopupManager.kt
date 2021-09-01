package pdf.reader.simplepdfreader.tools

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.widget.*
import com.github.barteksc.pdfviewer.PDFView
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.AutoSpacingStateCache
import pdf.reader.simplepdfreader.data.cache.DarkThemeCache
import pdf.reader.simplepdfreader.data.cache.HorizontalScrollingCache

interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val context: Context, private val darkThemeCache: DarkThemeCache,
               private val autoSpacingStateCache: AutoSpacingStateCache,
               private val horizontalScrollingCache: HorizontalScrollingCache,
               private val pdfView: PDFView
               ) :
        ReadingPopupManager {

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        override fun showPopupMenu() {
            val dialog = Dialog(context,R.style.BottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.reading_settings)

            val switchTheme = dialog.findViewById<Switch>(R.id.darkTheme)
            val switchAutoSpacing = dialog.findViewById<Switch>(R.id.AutoSpacing)
            val switchScrolling = dialog.findViewById<Switch>(R.id.horizontalScroll)

            switchTheme.isChecked = darkThemeCache.read()
            switchAutoSpacing.isChecked = autoSpacingStateCache.read()
            switchScrolling.isChecked = horizontalScrollingCache.read()

            themeControl(switchTheme)
            spacingControl(switchAutoSpacing)
            scrollingControl(switchScrolling)

            dialog.show()
        }
        private fun themeControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    darkThemeCache.save(true)
                    pdfView.setNightMode(true)

                } else {
                    darkThemeCache.save(false)
                    pdfView.setNightMode(false)
                }
            }
        }
        private fun spacingControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    autoSpacingStateCache.save(true)
                    pdfView.setPageSnap(true)
                    pdfView.setPageFling(true)

                } else {
                    autoSpacingStateCache.save(false)
                    pdfView.setPageSnap(false)
                    pdfView.setPageFling(false)
                }
            }
        }
        private fun scrollingControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    horizontalScrollingCache.save(true)
                    pdfView.isNestedScrollingEnabled = true

                } else {
                    horizontalScrollingCache.save(false)
                    pdfView.isNestedScrollingEnabled = false
                }
            }
        }
    }
}
