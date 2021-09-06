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

        binding.toolBarMain.backBtn.setOnClickListener {
            finish()
        }

        binding.openCamera.setOnClickListener {
            val values = ContentValues()
            values.put(Images.Media.TITLE, "Image From PdfReader")
            values.put(Images.Media.DESCRIPTION, "Image that was converted to .pdf")
            imageUri = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI,values)!!
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, 15)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 15) {
            val bitmap = Images.Media.getBitmap(contentResolver, imageUri)
            binding.imageView.setImageBitmap(bitmap)
            ImageToPdfConverter.Base(nameGenerator)
                .convert(ImageSaver.Base(this)
                    .saveBitmap(binding.imageView))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}

