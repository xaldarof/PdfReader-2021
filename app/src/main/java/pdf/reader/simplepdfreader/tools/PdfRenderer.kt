package pdf.reader.simplepdfreader.tools

import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import java.io.File

interface PdfRenderer {

    fun getBitmap(path: String, page: Int): Bitmap

    class Base : PdfRenderer {

        override fun getBitmap(path: String, page: Int): Bitmap {
            val fileDescriptor =
                ParcelFileDescriptor.open(File(path), ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = android.graphics.pdf.PdfRenderer(fileDescriptor)
            val page = pdfRenderer.openPage(20)
            val bitmap =
                Bitmap.createBitmap(page.width / 2, page.height / 2, Bitmap.Config.ARGB_8888)
            page.render(
                bitmap,
                null,
                null,
                android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
            )

            page.close()
            pdfRenderer.close()

            return bitmap
        }
    }
}