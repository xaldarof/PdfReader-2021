package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.os.Environment
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

interface ImageSaver {

    fun saveBitmap(image: ImageView): File

    class Base(private val context: Context) : ImageSaver {

        private lateinit var fileOutputStream: FileOutputStream

        override fun saveBitmap(image: ImageView): File {
                val bitmapDrawable = image.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap

                val filePath = Environment.getExternalStorageDirectory()
                val dir = File(filePath.absolutePath + "/Download/")
                dir.mkdir()
                val file = File(dir, "${System.currentTimeMillis()}.jpg")

                try {
                    fileOutputStream = FileOutputStream(file)
                } catch (e: FileNotFoundException) {
                }

                if (bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)==true){
                    Toast.makeText(context, "Сохранено в :$file", Toast.LENGTH_LONG).show()
                }
                fileOutputStream.flush()
                fileOutputStream.close()

            return file
        }
    }
}