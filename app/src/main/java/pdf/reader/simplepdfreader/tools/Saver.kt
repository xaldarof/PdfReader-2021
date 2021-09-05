package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.os.Environment

import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


interface Saver {

    fun saveBitmap(bmp: Bitmap, fileName: String): File

    class Base : Saver{

        override fun saveBitmap(bmp: Bitmap, fileName: String): File {
            val bytes = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val createdDirectory = File("${Environment.getExternalStorageDirectory()}/screens/")
            createdDirectory.mkdir()

            val fileToSave = File(createdDirectory,"$fileName.jpg")

            Log.d("pos","IS SAVED ${fileToSave.createNewFile()}  PATH = ${fileToSave.absolutePath}")
            val fo = FileOutputStream(fileToSave)
            fo.write(bytes.toByteArray())
            fo.close()
            return fileToSave
        }
    }
}