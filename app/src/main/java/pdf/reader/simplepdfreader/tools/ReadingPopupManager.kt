package pdf.reader.simplepdfreader.tools

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.view.Window
import android.widget.Switch
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.snackbar.Snackbar
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.AutoSpacingStateCache
import pdf.reader.simplepdfreader.data.cache.DarkThemeCache
import pdf.reader.simplepdfreader.data.cache.HorizontalScrollingCache
import pdf.reader.simplepdfreader.databinding.ReadingSettingsBinding
import com.itextpdf.text.pdf.parser.PdfTextExtractor

import com.itextpdf.text.pdf.PdfReader
import java.io.*
import java.lang.Exception
import java.net.URL
import android.graphics.RectF
import android.os.ParcelFileDescriptor
import com.google.api.Page

import com.itextpdf.text.pdf.PdfPage





interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val context: Context, private val darkThemeCache: DarkThemeCache,
               private val autoSpacingStateCache: AutoSpacingStateCache,
               private val horizontalScrollingCache: HorizontalScrollingCache,
               private val pdfView: PDFView,
               private val path:String) : ReadingPopupManager {

        private val dataShare = DataShare.Base(context)
        private val textExtractor = TextExtracter.Base()
        private val textCopyManager = TextCopyManager.Base(context)
        private val pdfRenderer = PdfRenderer.Base()
        private val screenShot = ScreenShot.Base(pdfRenderer)
        private val imageSaver = Saver.Base()

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
                textCopyManager.copyToClipboard(textExtractor.extractText(path,pdfView.currentPage+1))
                Snackbar.make(dialog.container,"Текст скопирован в буфер обмена",Snackbar.LENGTH_SHORT)
                    .show()
            }

            dialog.getScreenBtn.setOnClickListener {
                dialog.image.setImageBitmap(screenShot.makeScreenShot(path,10))
                imageSaver.saveBitmap(screenShot.makeScreenShot(path,10),"PHOTO2")
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
