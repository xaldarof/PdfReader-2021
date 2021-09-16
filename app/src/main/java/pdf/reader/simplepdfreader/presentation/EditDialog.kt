package pdf.reader.simplepdfreader.presentation

import android.content.Context
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.EditLayoutBinding
import java.io.File

interface EditDialog {

    fun showDialog()

    @KoinApiExtension
    class Base(context: Context,private val pdfFileDb: PdfFileDb) :
        BottomSheetDialog(context, R.style.BottomSheetDialogTheme), EditDialog, KoinComponent {

        private val pdfFilesRepository: PdfFilesRepository by inject()

        override fun showDialog() {
            val binding = EditLayoutBinding.inflate(layoutInflater)
            binding.newNameEditText.setText(pdfFileDb.name)

            binding.saveBtn.setOnClickListener {
                if (binding.newNameEditText.text.toString().isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        pdfFilesRepository.updatePath(pdfFileDb.dirName, binding.newNameEditText.text.toString())
                        File(File(pdfFileDb.dirName).absolutePath).renameTo(File("${File(pdfFileDb.dirName).absoluteFile}/${binding.newNameEditText.text}"))
                    }
                    Toast.makeText(context, "Успешно изменено!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }

            binding.deleteBtn.setOnClickListener {
                File(pdfFileDb.dirName).delete()
                CoroutineScope(Dispatchers.IO).launch {
                    pdfFilesRepository.deletePdfFile(pdfFileDb)
                }
                dismiss()
            }

            setContentView(binding.root)
            show()
        }
    }
}