package pdf.reader.simplepdfreader.presentation

import android.R
import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.databinding.ActivityMainBinding
import pdf.reader.simplepdfreader.presentation.adapter.FragmentController
import pdf.reader.simplepdfreader.tools.PermissionManager
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.flow.collect
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.domain.MainActivityViewModel
import pdf.reader.simplepdfreader.tools.AddManager
import pdf.reader.simplepdfreader.tools.FragmentChanger
import java.lang.ref.WeakReference

@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val pdfFilesRepository: PdfFilesRepository by inject()
    private val viewModel: MainActivityViewModel by viewModels()
    private val fragments = arrayListOf<Fragment>(
        CoreFragment(), FavoriteFragment(),
        NewPdfFilesFragment(), InterestingFragment(), WillReadFragment(), DoneFragment())

    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AddManager.Base(this,binding.adView).init()

        FragmentController(WeakReference(this), pdfFilesRepository, fragments)
        val permissionManager = PermissionManager.Base(WeakReference(this))
        permissionManager.requestPermission()

        val searchFragment = SearchFragment()
        binding.toolBarMain.searchBtn.setOnClickListener {
            val fragmentChanger = FragmentChanger.Base(WeakReference(this))
            fragmentChanger.replace(searchFragment)
        }

        binding.toolBarMain.openConverter.setOnClickListener {
            startActivity(Intent(this,ConverterActivity::class.java))
            finish()
        }

        binding.toolBarMain.scan.setOnClickListener {
            if (permissionManager.checkPermission()) {
                startScanning()
            } else {
                permissionManager.requestPermission()
            }
        }
    }

    private fun startScanning() {
        CoroutineScope(Dispatchers.IO).launch {
            pdfFilesRepository.findFilesAndInsert(Environment.getDataDirectory())
        }
        val intent = Intent(this, UpdatingActivity::class.java)
        startActivity(intent)
    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 20) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                startScanning()
            }

        } else {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 15) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    val dirName = data.data!!
                    viewModel.insertSinglePdfFile(
                        PdfFileDb(
                            dirName.path.toString(),
                            "file.name",
                            favorite = false,
                            reading = false,
                            finished = false,
                            lastPage = 0,
                            isEverOpened = false,
                            pageCount = 0,
                            interesting = false,
                            size = "",
                            willRead = false
                        )
                    )
                }
            }
        }
    }
}