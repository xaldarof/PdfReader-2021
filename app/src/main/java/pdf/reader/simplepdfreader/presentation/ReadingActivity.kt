package pdf.reader.simplepdfreader.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.databinding.ActivityReadingBinding
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.presentation.viewmodels.CountModel
import pdf.reader.simplepdfreader.presentation.viewmodels.PdfFileModel
import pdf.reader.simplepdfreader.presentation.viewmodels.ReadingActivityViewModel
import java.io.File
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.core.CacheRepository
import pdf.reader.simplepdfreader.data.core.ReadingFileRepository
import pdf.reader.simplepdfreader.domain.PdfFileToPdfFileDbMapper
import pdf.reader.simplepdfreader.presentation.dialogs.ErrorShower
import pdf.reader.simplepdfreader.presentation.dialogs.ReadingPopupManager
import pdf.reader.simplepdfreader.presentation.dialogs.WaitingDialogShower
import pdf.reader.simplepdfreader.tools.BarAnimator
import java.lang.ref.WeakReference
import java.util.*

@KoinApiExtension
class ReadingActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityReadingBinding
    private val viewModel: ReadingActivityViewModel by viewModels()
    private val counterLiveData = MutableLiveData<CountModel>()
    private val date = Date().time
    private var dirName = ""
    private lateinit var pdfFile: PdfFileModel
    private lateinit var barAnimator: BarAnimator
    private lateinit var waitingDialogShower: WaitingDialogShower.Base
    private val pdfFilesRepository: ReadingFileRepository by inject()
    private val cacheRepository: CacheRepository by inject()
    private val mapper = PdfFileToPdfFileDbMapper.Base()

    @RequiresApi(Build.VERSION_CODES.M)
    @KoinApiExtension
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pdfFile = intent.getSerializableExtra("pdf") as PdfFileModel
        supportActionBar?.hide()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        waitingDialogShower = WaitingDialogShower.Base(this)

        viewModel.updateIsEverOpened(pdfFile.dirName, true)
        viewModel.updateLastReadTime(pdfFile.dirName, date)
        binding.seekBar.max = pdfFile.pageCount
        binding.seekBar.progress = pdfFile.lastPage
        binding.fileName.text = pdfFile.name
        this.dirName = pdfFile.dirName
        binding.counter.text = "${pdfFile.lastPage} из ${binding.pdfView.pageCount}"
        binding.pdfView.useBestQuality(true)

        viewModel.getPageState().observe(this, {
            binding.seekBar.progress = it
        })

        val readingPopupManager = ReadingPopupManager.Base(
            this,cacheRepository, binding.pdfView, dirName)

        barAnimator = BarAnimator.Base(binding.containerBottom, binding.containerTop)

        cacheRepository.apply {
            updateData(pdfFile.dirName, pdfFile.lastPage, readDarkThemeCache(),
                readAutoSpacingCache(), readHorScrollCache())
        }

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
            barAnimator.check()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.setPage(p1)
                viewModel.setPageState(p1)
                counterLiveData.value = CountModel(p1, binding.pdfView.pageCount)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateData(pdfFile.dirName, viewModel.getPage())
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("page", binding.pdfView.currentPage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        updateData(dirName, savedInstanceState.getInt("page"))
    }

    private fun pageObserve(): MutableLiveData<CountModel> {
        return counterLiveData
    }

    private fun updateData(
        dirName: String, lastPage: Int, nightMode: Boolean = false,
        pageSnap: Boolean = false, horizontalScroll: Boolean = false
    ) {
        binding.pdfView.useBestQuality(true)
        binding.pdfView.fromFile(File(dirName))
            .defaultPage(lastPage)
            .nightMode(nightMode)
            .enableAntialiasing(true)
            .autoSpacing(pageSnap)
            .pageSnap(pageSnap)
            .autoSpacing(pageSnap)
            .pageFling(pageSnap)
            .onRender {
                waitingDialogShower.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    waitingDialogShower.dismiss()
                }, 1000)
            }

            .swipeHorizontal(horizontalScroll)
            .enableAntialiasing(true)
            .enableAnnotationRendering(true)
            .onPageChange { page, pageCount ->
                viewModel.updateLastPage(dirName, page)
                viewModel.updatePageCount(dirName, pageCount)
                binding.seekBar.progress = page
                counterLiveData.value = CountModel(page, pageCount)
                binding.seekBar.max = pageCount
                viewModel.setPageState(page)
            }
            .onError {
                ErrorShower.Base(WeakReference(this), mapper.map(pdfFile),pdfFilesRepository).show()
            }
            .load()
    }
}
