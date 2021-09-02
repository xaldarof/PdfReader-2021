package pdf.reader.simplepdfreader.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.*
import pdf.reader.simplepdfreader.domain.CountModel
import pdf.reader.simplepdfreader.domain.PdfFileModel
import pdf.reader.simplepdfreader.domain.ReadingActivityViewModel
import pdf.reader.simplepdfreader.tools.ReadingPopupManager
import java.io.File
import android.content.Intent
import org.koin.core.component.KoinApiExtension


class ReadingActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityReadingBinding
    private val viewModel: ReadingActivityViewModel by viewModels()
    private val counterLiveData = MutableLiveData<CountModel>()
    private var isOpen = true
    private var page = 0
    private var dirName = ""

    @KoinApiExtension
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
        this.dirName = pdfFile.dirName
        binding.counter.text = "${pdfFile.lastPage} из ${pdfFile.pageCount}"

        val sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE)
        val darkThemeCache = DarkThemeCache(DarkThemeCacheImpl(sharedPreferences))
        val autoSpacingStateCache = AutoSpacingStateCache(AutoSpacingStateCacheImpl(sharedPreferences))
        val horizontalScrollingCache = HorizontalScrollingCache(HorizontalScrollingCacheImpl(sharedPreferences))
        val readingPopupManager = ReadingPopupManager.Base(this, darkThemeCache,autoSpacingStateCache,horizontalScrollingCache,binding.pdfView)

        updateData(pdfFile.dirName, pdfFile.lastPage, darkThemeCache.read(),autoSpacingStateCache.read(),horizontalScrollingCache.read())

        binding.menuBtn.setOnClickListener {
            readingPopupManager.showPopupMenu()
        }

        pageObserve().observe(this, {
            binding.counter.text = "${it.page} из ${it.pageCount}"

        })
        binding.backBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()

        }

        binding.pdfView.setOnClickListener {
            isOpen = if (isOpen) {
                binding.containerTop.animate().translationY(-200F)
                binding.containerBottom.animate().translationY(200F)
                false

            } else {
                binding.containerTop.animate().translationY(0F)
                binding.containerBottom.animate().translationY(0F)
                true

            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                page = p1
                counterLiveData.value = CountModel(p1, pdfFile.pageCount)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateData(pdfFile.dirName, page)
                binding.seekBar.progress = page
            }
        })
    }

    private fun pageObserve(): MutableLiveData<CountModel> {
        return counterLiveData
    }

    private fun updateData(dirName: String, lastPage: Int, nightMode: Boolean = false,pageSnap:Boolean=false,horizontalScroll:Boolean=false) {
        binding.pdfView.fromFile(File(dirName))
            .defaultPage(lastPage)
            .nightMode(nightMode)
            .enableAntialiasing(true)
            .autoSpacing(pageSnap)
            .pageSnap(pageSnap)
            .autoSpacing(pageSnap)
            .pageFling(pageSnap)
            .swipeHorizontal(horizontalScroll)
            .enableAnnotationRendering(true)
            .onPageChange { page, pageCount ->
                viewModel.updateLastPage(dirName, page)
                viewModel.updatePageCount(dirName, pageCount)
                binding.seekBar.progress = page
                counterLiveData.value = CountModel(page,pageCount)
                binding.seekBar.max = pageCount
            }.load()
        binding.pdfView.useBestQuality(true)
    }

    @KoinApiExtension
    override fun onBackPressed() {
        super.onBackPressed()
//        val intent = Intent(this,MainActivity::class.java)
//        startActivity(intent)
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
//        finish()
    }
}
