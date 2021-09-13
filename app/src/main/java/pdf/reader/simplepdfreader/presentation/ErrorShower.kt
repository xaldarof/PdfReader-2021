package pdf.reader.simplepdfreader.presentation

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.ReadingFileRepository
import pdf.reader.simplepdfreader.tools.DirectoryDeleter
import java.io.File
import java.lang.ref.WeakReference

interface ErrorShower {

    fun show()

    @KoinApiExtension
    class Base(private val weakReference: WeakReference<Activity>, private val dirName: String) :
        ErrorShower, KoinComponent {

        private val repository: ReadingFileRepository by inject()

        override fun show() {
            val dialog = Dialog(weakReference.get()!!, R.style.BottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.error_layout)
            val scanButton = dialog.findViewById<AppCompatButton>(R.id.scanBtn)
            val deleteButton = dialog.findViewById<AppCompatButton>(R.id.deleteBtn)

            scanButton.setOnClickListener {
                weakReference.get()!!.startActivity(Intent(weakReference.get()!!, UpdatingActivity::class.java))
                weakReference.get()!!.finish()
            }

            deleteButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (File(dirName).isDirectory) {
                        DirectoryDeleter.Base().delete(dirName)
                    }
                    repository.deletePdfFile(dirName)
                    weakReference.get()!!.finish()
                }
            }

            dialog.show()
        }
    }
}