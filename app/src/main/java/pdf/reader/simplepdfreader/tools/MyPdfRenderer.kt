package pdf.reader.simplepdfreader.tools

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import android.R
import java.lang.NullPointerException

class MyPdfRenderer(private val context: Context) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getBitmap(file: File?, imageView: ImageView): Bitmap? {
        if (file!!.exists()) {
            CoroutineScope(Dispatchers.Default).launch {
                val pageNum = 0
                val pdfiumCore = PdfiumCore(context)
                try {
                    val pdfDocument: PdfDocument = pdfiumCore.newDocument(openFile(file))
                    pdfiumCore.openPage(pdfDocument, pageNum)
                    val width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum)/2
                    val height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum)/2

                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

                    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 0, 0, width, height)

                    pdfiumCore.closeDocument(pdfDocument)

                    setImage(bitmap, imageView)

                } catch (ex: NullPointerException) {
                    ex.printStackTrace()
                }
            }
        }else {
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.stat_notify_error)

            return bitmap
        }
        return null
    }

    private fun setImage(bitmap: Bitmap?, imageView: ImageView){
        CoroutineScope(Dispatchers.Main).launch {
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun openFile(file: File?): ParcelFileDescriptor? {
        return try {
            ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
    }


}