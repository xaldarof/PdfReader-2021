package pdf.reader.simplepdfreader.presentation

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.core.ReadingFileRepository
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.tools.DirectoryDeleter
import java.io.File
import java.lang.ref.WeakReference

interface ErrorShower {

    fun show()

    class Base(private val weakReference: WeakReference<Activity>, private val pdfFileDb: PdfFileDb,
               private val repository: ReadingFileRepository
    ) :
        ErrorShower {

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
                    if (File(pdfFileDb.dirName).isDirectory) {
                        DirectoryDeleter.Base().delete(pdfFileDb.dirName)
                    }
                    repository.deletePdfFile(pdfFileDb)
                    weakReference.get()!!.finish()
                }
            }

            dialog.show()
        }
    }
}