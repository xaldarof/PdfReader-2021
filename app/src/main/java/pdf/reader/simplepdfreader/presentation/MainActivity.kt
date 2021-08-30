package pdf.reader.simplepdfreader.presentation

import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.databinding.ActivityMainBinding
import pdf.reader.simplepdfreader.presentation.adapter.FragmentController
import pdf.reader.simplepdfreader.tools.PermissionManager
import android.R.menu
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.tools.PopupMenuManager


@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val pdfFilesRepository: PdfFilesRepository by inject()

    @DelicateCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progress.visibility = View.INVISIBLE
        supportActionBar?.hide()
        FragmentController(this)
        val permissionManager = PermissionManager.Base(this)
        permissionManager.requestPermission()
        val popupMenu = PopupMenuManager.Base(this,permissionManager,pdfFilesRepository)
        popupMenu.showPopup(binding.toolBarMain.menuBtn,binding.progress)

    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
            }

        } else {
        }
    }
}