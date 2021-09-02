package pdf.reader.simplepdfreader.presentation

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
import com.google.android.material.snackbar.Snackbar
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.domain.MainActivityViewModel
import pdf.reader.simplepdfreader.tools.Animator
import java.lang.ref.WeakReference

//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "application/pdf"
//            startActivityForResult(intent, 15)
@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val pdfFilesRepository: PdfFilesRepository by inject()
    private val viewModel: MainActivityViewModel by viewModels()
    private val animator = Animator.Base()

    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        FragmentController(this)
        val permissionManager = PermissionManager.Base(WeakReference(this))

        permissionManager.requestPermission()

        val searchFragment = SearchFragment()

        binding.toolBarMain.searchBtn.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout,searchFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.toolBarMain.scan.setOnClickListener {
            if (permissionManager.checkPermission()) {
                startScanning()
            } else {
                permissionManager.requestPermission()
            }
        }
    }

    private fun startScanning(){
        CoroutineScope(Dispatchers.IO).launch {
            pdfFilesRepository.findFilesAndInsert(Environment.getExternalStorageDirectory())
        }
        animator.animate(binding.toolBarMain.scan)
        Snackbar.make(binding.layout,"Сканирование...",Snackbar.LENGTH_LONG).show()
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
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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
                            "file.name", favorite = false, reading = false, finished = false, lastPage = 0,
                            isEverOpened = false, pageCount = 0, interesting = false, size = "", willRead = false
                        )
                    )
                }
            }
        }
    }
}