package pdf.reader.simplepdfreader.presentation

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
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import org.koin.core.component.get
import pdf.reader.simplepdfreader.tools.AddManager
import pdf.reader.simplepdfreader.tools.Animator
import pdf.reader.simplepdfreader.tools.FragmentChanger
import java.lang.ref.WeakReference

@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val pdfFilesRepository = get<PdfFilesRepository>()

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AddManager.Base(this,binding.adView).init()


       FragmentController(WeakReference(this), pdfFilesRepository, fragments)
        val permissionManager = PermissionManager.Base(WeakReference(this))
        permissionManager.requestPermission()

        val searchFragment = SearchFragment()
        binding.toolBarMain.searchBtn.setOnClickListener {
            val fragmentChanger = FragmentChanger.Base(WeakReference(this))
            fragmentChanger.replace(searchFragment)
        }

        binding.toolBarMain.openReporter.setOnClickListener {
            startActivity(Intent(this,ReportActivity::class.java))
        }

        binding.toolBarMain.openConverter.setOnClickListener {
            startActivity(Intent(this,ConverterActivity::class.java))
        }

        binding.toolBarMain.scan.setOnClickListener {
            if (permissionManager.checkPermission()) {
                Animator.Base().animate(binding.toolBarMain.scan)

            } else {
                permissionManager.requestPermission()
            }
        }
    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 20) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED
                && grantResults[1] == PERMISSION_GRANTED && grantResults[2] == PERMISSION_GRANTED
            ) {
                Animator.Base().animate(binding.toolBarMain.scan)
            }

        } else {
        }
    }
}