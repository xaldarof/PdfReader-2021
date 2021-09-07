package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.os.Environment
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.PdfFilesRepository


interface ImageSaver {

    fun saveBitmap(image: ImageView) : File

    class Base(private val context: Context) : ImageSaver,KoinComponent {

        private lateinit var fileOutputStream: FileOutputStream
        private val pdfFilesRepository : PdfFilesRepository by inject()

        override fun saveBitmap(image: ImageView) : File{
            val bitmapDrawable = image.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap

            val filePath = Environment.getExternalStorageDirectory()
            val dir = File(filePath.absolutePath + "/Download/")
            dir.mkdir()
            val file = File(dir, "${System.currentTimeMillis()}.jpg")

            try {
                fileOutputStream = FileOutputStream(file)
            } catch (e: FileNotFoundException) {}

            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)

            Toast.makeText(context, "Сохранено в :$file", Toast.LENGTH_SHORT).show()
            Log.d("pos", "SAVED IN ${file.absolutePath}")

            fileOutputStream.flush()
            fileOutputStream.close()

            return file
        }
    }
}