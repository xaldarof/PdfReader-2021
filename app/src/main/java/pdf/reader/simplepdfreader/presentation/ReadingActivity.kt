package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.io.File


class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val action = intent.action

        if (Intent.ACTION_VIEW == action) {
            val uri: Uri? = intent.data
            File(uri!!.path)
            Log.d("pdf", "PATH =  $action")
        } else {
            Log.d("pdf", "intent was something else: $action")
        }

    }
}