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
import androidx.appcompat.app.AppCompatDelegate
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.tools.ImageToPdfConverter
import pdf.reader.simplepdfreader.tools.NameGenerator

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding
    private val nameGenerator = NameGenerator.Base()

    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        binding.saveBtn.visibility = View.INVISIBLE

        binding.toolBarMain.backBtn.setOnClickListener {
            finish()
        }

        binding.openBtn.setOnClickListener {
           openCropActivity()
        }
        binding.imageView.setOnClickListener {
            openCropActivity()
        }
    }

    private fun openCropActivity() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val cropImage = CropImage.getActivityResult(data)
            val uri = cropImage.uri
            binding.imageView.setImageURI(uri)
            binding.saveBtn.visibility = View.VISIBLE

            binding.saveBtn.setOnClickListener {
                ImageToPdfConverter.Base(nameGenerator)
                    .convert(
                        ImageSaver.Base(this)
                            .saveBitmap(binding.imageView))
            }
        }
    }
}

