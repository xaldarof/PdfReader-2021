package pdf.reader.simplepdfreader.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.databinding.EditLayoutBinding

interface EditDialog {

    fun showDialog()

    @KoinApiExtension
    class Base(context: Context, private val dir: String,private val oldName:String) :
        BottomSheetDialog(context, R.style.BottomSheetDialogTheme), EditDialog, KoinComponent {

        private val pdfFilesRepository: PdfFilesRepository by inject()

        override fun showDialog() {
            val binding = EditLayoutBinding.inflate(layoutInflater)
            binding.newNameEditText.setText(oldName)
            Log.d("pos",dir)

            binding.saveBtn.setOnClickListener {
                if (binding.newNameEditText.text.toString().isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        pdfFilesRepository.updatePath(dir, binding.newNameEditText.text.toString())
                    }
                    Toast.makeText(context, "Успешно изменено!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
            setContentView(binding.root)
            show()
        }
    }
}