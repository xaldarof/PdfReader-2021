package pdf.reader.simplepdfreader.presentation

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.Window
import android.widget.Switch
import androidx.annotation.RequiresApi
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.AutoSpacingStateCache
import pdf.reader.simplepdfreader.data.cache.DarkThemeCache
import pdf.reader.simplepdfreader.data.cache.HorizontalScrollingCache
import pdf.reader.simplepdfreader.databinding.ReadingSettingsBinding
import pdf.reader.simplepdfreader.tools.*


interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val context: Context, private val darkThemeCache: DarkThemeCache,
               private val autoSpacingStateCache: AutoSpacingStateCache,
               private val horizontalScrollingCache: HorizontalScrollingCache,
               private val pdfView: PDFView,
               private val path:String) : ReadingPopupManager {

        private val dataShare = FileDataShare.Base(context)
        private val textExtractor = TextExtracter.Base()
        private val textCopyManager = TextCopyManager.Base(context)
        private val imageSaver = ImageSaver.Base(context)
        private val pdfRenderer = PdfRenderer.Base()

        @RequiresApi(Build.VERSION_CODES.M)
        override fun showPopupMenu() {
            val dialogMain = Dialog(context,R.style.BottomSheetDialogTheme)
            val dialog = ReadingSettingsBinding.inflate(dialogMain.layoutInflater)
            dialogMain.requestWindowFeature(Window.FEATURE_NO_TITLE)
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

            dialog.getTextBtn.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    textCopyManager.copyToClipboard(textExtractor.extractText(path, pdfView.currentPage + 1))
                }
                dialogMain.dismiss()
            }

            dialog.getScreenBtn.setOnClickListener {
                ImageSaveDialog.Base(pdfRenderer,imageSaver,context).show(path,pdfView.currentPage)
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
