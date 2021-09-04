package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import pdf.reader.simplepdfreader.databinding.ActivityConverterBinding
import android.content.Context
import android.database.Cursor
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import android.provider.MediaStore.Images
import java.io.ByteArrayOutputStream
import android.graphics.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.openCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 11)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11) {
            val bitmap: Bitmap = data!!.extras!!.get("data") as Bitmap
            val tempUri: Uri = getImageUri(this, bitmap)!!
            val finalFile = File(getRealPathFromURI(tempUri))

            binding.imageView.setImageBitmap(bitmap)
            convertImageTopPdf(finalFile)
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri?): String {
        var path = ""
        if (contentResolver != null) {
            val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    private fun convertImageTopPdf(file: File) {
        val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)

        val document = PdfDocument()
        val pageInfo: PdfDocument.PageInfo =
            PdfDocument.PageInfo.Builder(100, 100, 0).create()
        val page: PdfDocument.Page = document.startPage(pageInfo)

        val canvas: Canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        document.finishPage(page)

        val directoryPath: String = Environment.getExternalStorageDirectory().toString()
        document.writeTo(FileOutputStream("$directoryPath/exa11mpl11e.pdf"))
        document.close()
    }
}

