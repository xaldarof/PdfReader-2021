package pdf.reader.simplepdfreader.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.data.cache.*
import pdf.reader.simplepdfreader.domain.CountModel
import pdf.reader.simplepdfreader.domain.PdfFileModel
import pdf.reader.simplepdfreader.domain.ReadingActivityViewModel
import pdf.reader.simplepdfreader.tools.ReadingPopupManager
import java.io.File
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.tools.ErrorShower
import java.lang.ref.WeakReference
import java.util.*

@KoinApiExtension
class ReadingActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityReadingBinding
    private val viewModel: ReadingActivityViewModel by viewModels()
    private val counterLiveData = MutableLiveData<CountModel>()
    private val date = Date().time
    private var isOpen = true
    private var dirName = ""
    private lateinit var pdfFile: PdfFileModel

    @KoinApiExtension
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pdfFile = intent.getSerializableExtra("pdf") as PdfFileModel
        supportActionBar?.hide()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        viewModel.updateIsEverOpened(pdfFile.dirName,true)
        viewModel.updateLastReadTime(pdfFile.dirName,date)
        binding.seekBar.max = pdfFile.pageCount
        binding.seekBar.progress = pdfFile.lastPage
        binding.fileName.text = pdfFile.name
        this.dirName = pdfFile.dirName
        binding.counter.text = "${pdfFile.lastPage} из ${pdfFile.pageCount}"

        val sharedPreferences = getSharedPreferences("cache", MODE_PRIVATE)
        val darkThemeCache = DarkThemeCache(DarkThemeCacheImpl(sharedPreferences))
        val autoSpacingStateCache = AutoSpacingStateCache(AutoSpacingStateCacheImpl(sharedPreferences))
        val horizontalScrollingCache = HorizontalScrollingCache(HorizontalScrollingCacheImpl(sharedPreferences))
        val readingPopupManager = ReadingPopupManager.Base(this, darkThemeCache,autoSpacingStateCache,horizontalScrollingCache,binding.pdfView,dirName)

        updateData(pdfFile.dirName, pdfFile.lastPage, darkThemeCache.read(),autoSpacingStateCache.read(),horizontalScrollingCache.read())

        binding.menuBtn.setOnClickListener {
            readingPopupManager.showPopupMenu()
        }

        pageObserve().observe(this, {
            binding.counter.text = "${it.page} из ${it.pageCount}"

        })
        binding.backBtn.setOnClickListener {
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
                viewModel.updatePage(p1)
                counterLiveData.value = CountModel(p1, pdfFile.pageCount)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateData(pdfFile.dirName, viewModel.getPage())
                binding.seekBar.progress = viewModel.getPage()
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        updateData(dirName,viewModel.getPage())
    }

    private fun pageObserve(): MutableLiveData<CountModel> {
        return counterLiveData
    }

    private fun updateData(dirName: String, lastPage: Int, nightMode: Boolean = false,pageSnap:Boolean=false,horizontalScroll:Boolean=false) {
        binding.pdfView.useBestQuality(true)
        binding.pdfView.fromFile(File(dirName))
            .defaultPage(lastPage)
            .nightMode(nightMode)
            .enableAntialiasing(true)
            .autoSpacing(pageSnap)
            .pageSnap(pageSnap)
            .autoSpacing(pageSnap)
            .pageFling(pageSnap)
            .swipeHorizontal(horizontalScroll)
            .enableAntialiasing(true)
            .spacing(100)
            .enableAnnotationRendering(true)
            .onPageChange { page, pageCount ->
                viewModel.updateLastPage(dirName, page)
                viewModel.updatePageCount(dirName, pageCount)
                binding.seekBar.progress = page
                counterLiveData.value = CountModel(page,pageCount)
                binding.seekBar.max = pageCount
            }
            .onError {
                ErrorShower.Base(WeakReference(this),dirName).show()
            }
            .load()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
