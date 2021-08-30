package pdf.reader.simplepdfreader.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent
import org.koin.core.component.bind
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.domain.CountModel
import pdf.reader.simplepdfreader.domain.PdfFileModel
import pdf.reader.simplepdfreader.domain.ReadingActivityViewModel
import java.io.File

class ReadingActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityReadingBinding
    private val viewModel: ReadingActivityViewModel by viewModels()
    private var isOpen = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pdfFile = intent.getSerializableExtra("pdf") as PdfFileModel
        supportActionBar?.hide()
        binding.seekBar.max = pdfFile.pageCount
        binding.seekBar.progress = pdfFile.lastPage
        binding.fileName.text = pdfFile.name
        updateData(pdfFile.dirName,pdfFile.lastPage)

        pageObserve().observe(this,{
            binding.counter.text = "${it.page} из ${it.pageCount}"
        })

        binding.pdfView.setOnClickListener {
            if (isOpen) {
                binding.containerTop.animate().translationY(-200F)
                binding.containerBottom.animate().translationY(200F)
                isOpen = false

            } else {
                binding.containerTop.animate().translationY(0F)
                binding.containerBottom.animate().translationY(0F)
                isOpen = true

            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateData(pdfFile.dirName,p1)
                counterLiveData.value = CountModel(p1,pdfFile.pageCount)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }
    private val counterLiveData = MutableLiveData<CountModel>()
    private fun pageObserve():MutableLiveData<CountModel>{
        return counterLiveData
    }

    fun updateData(dirName:String,lastPage:Int){
        binding.pdfView.fromFile(File(dirName))
            .defaultPage(lastPage)
            .onPageChange { page, pageCount ->
                viewModel.updateLastPage(dirName, page)
                viewModel.updatePageCount(dirName, pageCount)
                binding.seekBar.max = pageCount
                counterLiveData.value = CountModel(page,pageCount)
            }
            .load()
    }

}