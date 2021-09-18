package pdf.reader.simplepdfreader.presentation.dialogs

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
import pdf.reader.simplepdfreader.data.core.CacheRepository
import pdf.reader.simplepdfreader.databinding.ReadingSettingsBinding
import pdf.reader.simplepdfreader.tools.*


interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val context: Context,
               private val cacheRepository: CacheRepository,
               private val pdfView: PDFView,
               private val path:String) : ReadingPopupManager {

        private val dataShare = FileDataShare.Base(context)
        private val textExtractor = TextExtracter.Base()
        private val textCopyManager = TextCopyManager.Base(context)
        private val imageSaver = ImageSaver.Base(context)
        private val pdfRenderer = PdfRenderer.Base()
        private val dialogShower = WaitingDialogShower.Base(context)

        @RequiresApi(Build.VERSION_CODES.M)
        override fun showPopupMenu() {
            val dialogMain = Dialog(context,R.style.BottomSheetDialogTheme)
            val dialog = ReadingSettingsBinding.inflate(dialogMain.layoutInflater)
            dialogMain.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMain.setContentView(dialog.root)

            dialog.darkTheme.isChecked = cacheRepository.readDarkThemeCache()
            dialog.AutoSpacing.isChecked = cacheRepository.readAutoSpacingCache()
            dialog.horizontalScroll.isChecked = cacheRepository.readHorScrollCache()

            themeControl(dialog.darkTheme)
            spacingControl(dialog.AutoSpacing)
            scrollingControl(dialog.horizontalScroll)

            dialog.shareBtn.setOnClickListener {
                dataShare.share(path)
            }

            dialog.getTextBtn.setOnClickListener {
                dialogShower.show()
                CoroutineScope(Dispatchers.Main).launch {
                    if (textCopyManager.copyToClipboard(textExtractor.extractText(path, pdfView.currentPage + 1))){
                        dialogShower.dismiss()
                    }
                }
                dialogMain.dismiss()
            }

            dialog.getScreenBtn.setOnClickListener {
                ImageSaveDialog.Base(pdfRenderer, imageSaver, context).show(path,pdfView.currentPage)
            }

            dialogMain.show()
        }


        private fun themeControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    cacheRepository.writeDarkThemeCache(true)
                    pdfView.setNightMode(true)

                } else {
                    cacheRepository.writeDarkThemeCache(false)
                    pdfView.setNightMode(false)
                }
            }
        }
        private fun spacingControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    cacheRepository.writAutoSpacingCache(true)
                    pdfView.setPageSnap(true)
                    pdfView.setPageFling(true)

                } else {
                    cacheRepository.writAutoSpacingCache(false)
                    pdfView.setPageSnap(false)
                    pdfView.setPageFling(false)
                }
            }
        }
        private fun scrollingControl(switch: Switch){
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    cacheRepository.writeHorScrollCache(true)
                    pdfView.isNestedScrollingEnabled = true

                } else {
                    cacheRepository.writeHorScrollCache(false)
                    pdfView.isNestedScrollingEnabled = false
                }
            }
        }
    }
}
