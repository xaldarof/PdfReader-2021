package pdf.reader.simplepdfreader.tools

import android.graphics.Bitmap

interface ScreenShot {

    fun makeScreenShot(path:String,page:Int) : Bitmap

    class Base(private val pdfRenderer: PdfRenderer) : ScreenShot {
        override fun makeScreenShot(path:String,page:Int) : Bitmap {
           return pdfRenderer.getBitmap(path,page)
        }
    }
}