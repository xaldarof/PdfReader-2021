package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.PdfFilesRepository

interface PopupMenuManager {

    fun showPopup(view: View,progressBar: ProgressBar)

    class Base(private val context: Context,private val permissionManager: PermissionManager,private val pdfFilesRepository: PdfFilesRepository) : PopupMenuManager {
        override fun showPopup(view: View,progressBar: ProgressBar) {
            view.setOnClickListener {
                val popupMenu = androidx.appcompat.widget.PopupMenu(context, view)
                popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.scan -> scan(progressBar)
                        R.id.setting -> true
                        else -> true
                    }
                }
            }
        }
        private fun scan(progressBar: ProgressBar) : Boolean{
            if (permissionManager.requestPermission()) {
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    pdfFilesRepository.findFilesAndInsert(Environment.getExternalStorageDirectory())
                }
                progressBar.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.INVISIBLE
                }, 3000)
            }
            return true
        }
    }
}