package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import pdf.reader.simplepdfreader.databinding.ActivityConverterBinding
import android.net.Uri
import android.provider.MediaStore.Images
import pdf.reader.simplepdfreader.tools.ImageSaver
import android.content.ContentValues
import android.R
import android.graphics.BitmapFactory
import android.view.View
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.tools.ImageToPdfConverter
import pdf.reader.simplepdfreader.tools.NameGenerator

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding
    private lateinit var imageUri: Uri
    private val nameGenerator = NameGenerator.Base()

    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        binding.saveBtn.visibility = View.INVISIBLE

        binding.toolBarMain.backBtn.setOnClickListener {
            finish()
        }

        binding.openGallery.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, GALLERY_REQUEST_CODE)
        }

        binding.openCamera.setOnClickListener {
            val values = ContentValues()
            values.put(Images.Media.TITLE, "Image From PdfReader")
            values.put(Images.Media.DESCRIPTION, "Image that was converted to .pdf")
            imageUri = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI,values)!!
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE  && resultCode == RESULT_OK) {
            val bitmap = Images.Media.getBitmap(contentResolver, imageUri)
            binding.imageView.setImageBitmap(bitmap)
            binding.saveBtn.visibility = View.VISIBLE

            binding.saveBtn.setOnClickListener {
                ImageToPdfConverter.Base(nameGenerator)
                    .convert(
                        ImageSaver.Base(this)
                            .saveBitmap(binding.imageView)
                    )
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val imgUri = data!!.data
            val inputStream = contentResolver.openInputStream(imgUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imageView.setImageBitmap(bitmap)
            binding.saveBtn.visibility = View.VISIBLE

            binding.saveBtn.setOnClickListener {
                ImageToPdfConverter.Base(nameGenerator)
                    .convert(
                        ImageSaver.Base(this)
                            .saveBitmap(binding.imageView)
                    )
            }
        }
    }
    private companion object {
        const val CAMERA_REQUEST_CODE = 11
        const val GALLERY_REQUEST_CODE = 12
    }
}

