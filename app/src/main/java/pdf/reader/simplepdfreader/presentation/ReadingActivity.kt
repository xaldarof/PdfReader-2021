package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import pdf.reader.simplepdfreader.domain.PdfFileModel
import java.io.File


class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pdfFile = intent.getSerializableExtra("pdf") as PdfFileModel

        binding.pdfView.fromFile(File(pdfFile.dirName)).load()

    }
}