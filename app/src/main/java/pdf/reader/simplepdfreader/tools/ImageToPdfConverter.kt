package pdf.reader.simplepdfreader.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

interface ImageToPdfConverter {

    fun convert(file: File)

    class Base(private val nameGenerator: NameGenerator) : ImageToPdfConverter{
        override fun convert(file:File) {
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)

                val document = PdfDocument()
                val pageInfo: PdfDocument.PageInfo =
                    PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 0).create()
                val page: PdfDocument.Page = document.startPage(pageInfo)

                val canvas: Canvas = page.canvas
                canvas.drawBitmap(bitmap, 0f, 0f, null)
                document.finishPage(page)

                val directoryPath: String = Environment.getExternalStorageDirectory().toString()
                document.writeTo(FileOutputStream("$directoryPath/${nameGenerator.getAutoName()}.pdf"))
                document.close()
            }
        }
    }
}