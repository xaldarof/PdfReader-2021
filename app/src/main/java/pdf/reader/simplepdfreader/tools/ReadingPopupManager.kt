package pdf.reader.simplepdfreader.tools

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Switch
import com.github.barteksc.pdfviewer.PDFView
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.AutoSpacingStateCache
import pdf.reader.simplepdfreader.data.cache.DarkThemeCache
import pdf.reader.simplepdfreader.data.cache.HorizontalScrollingCache
import pdf.reader.simplepdfreader.databinding.ReadingSettingsBinding

interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val context: Context, private val darkThemeCache: DarkThemeCache,
               private val autoSpacingStateCache: AutoSpacingStateCache,
               private val horizontalScrollingCache: HorizontalScrollingCache,
               private val pdfView: PDFView,
               private val path:String) : ReadingPopupManager {

        private val dataShare = DataShare.Base(context)

        override fun showPopupMenu() {
            val dialogMain = Dialog(context,R.style.BottomSheetDialogTheme)
            val dialog = ReadingSettingsBinding.inflate(dialogMain.layoutInflater)
            dialogMain.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMain.setCancelable(true)
            dialogMain.setContentView(dialog.root)

            dialog.darkTheme.isChecked = darkThemeCache.read()
            dialog.AutoSpacing.isChecked = autoSpacingStateCache.read()
            dialog.horizontalScroll.isChecked = horizontalScrollingCache.read()

            themeControl(dialog.darkTheme)
            spacingControl(dialog.AutoSpacing)
            scrollingControl(dialog.horizontalScroll)
            dialog.shareBtn.setOnClickListener {
                dataShare.share(path)
            }

            dialogMain.show()
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
