package pdf.reader.simplepdfreader.presentation

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import pdf.reader.simplepdfreader.databinding.ActivityMainBinding
import pdf.reader.simplepdfreader.presentation.adapter.FragmentController
import pdf.reader.simplepdfreader.tools.PermissionManager
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.presentation.dialogs.RequestPermissionDialog
import pdf.reader.simplepdfreader.presentation.fragments.*
import pdf.reader.simplepdfreader.tools.AdManager
import pdf.reader.simplepdfreader.tools.Animator
import pdf.reader.simplepdfreader.tools.FragmentChanger
import java.lang.ref.WeakReference

@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent, RequestPermissionDialog.Base.PermissionCallBack {

    private lateinit var binding: ActivityMainBinding
    private val pdfFilesRepository: PdfFilesRepository by inject()
    private var checkDoubleBackPress = false
    private val requestDialog = RequestPermissionDialog.Base(this)

    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AdManager.Base(this, binding.adView).init()
        requestDialog.registerCalLBack(this)

        val fragments = arrayListOf<Fragment>(
            CoreFragment(), FavoriteFragment(),
            NewPdfFilesFragment(), InterestingFragment(), WillReadFragment(), DoneFragment())

        FragmentController(this, pdfFilesRepository, fragments)
        val permissionManager = PermissionManager.Base(WeakReference(this),this)
        permissionManager.requestPermission()

        val searchFragment = SearchFragment()
        binding.toolBarMain.searchBtn.setOnClickListener {
            val fragmentChanger = FragmentChanger.Base(WeakReference(this))
            fragmentChanger.replace(searchFragment)
        }

        binding.toolBarMain.searchBook.setOnClickListener {
            startActivity(Intent(this, SearchBookActivity::class.java))
        }
        binding.toolBarMain.openReporter.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

        binding.toolBarMain.openConverter.setOnClickListener {
            startActivity(Intent(this, ConverterActivity::class.java))
        }

        binding.toolBarMain.scan.setOnClickListener {
            if (permissionManager.requestPermission()) {
                Animator.Base().animate(binding.toolBarMain.scan)
            } else {
                permissionManager.requestPermission()
            }
        }
    }

    override fun onBackPressed() {
        if (!checkDoubleBackPress){
            Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()
        }
        if (checkDoubleBackPress) {
            super.onBackPressed()
        }
        checkDoubleBackPress=true
        Handler(Looper.getMainLooper()).postDelayed({ checkDoubleBackPress=false },2000)
    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 20) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED
                && grantResults[1] == PERMISSION_GRANTED && grantResults[2] == PERMISSION_GRANTED) {
                Animator.Base().animate(binding.toolBarMain.scan)
            }
        }
    }

    override fun request(result: Boolean) {
        finish()
    }
}