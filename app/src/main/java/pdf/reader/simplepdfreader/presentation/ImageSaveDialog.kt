package pdf.reader.simplepdfreader.presentation

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.databinding.ImageSaveDialogBinding
import pdf.reader.simplepdfreader.tools.*


interface ImageSaveDialog {

    fun show(path: String, page: Int)

    class Base(
        private val pdfRenderer: PdfRenderer,
        private val imageSaver: ImageSaver,
        private val context: Context
    ) : ImageSaveDialog {

        private val dataShare = ImageDataShare.Base(context)
        private val textExtracter = TextExtracter.Base()
        private val textCopyManager = TextCopyManager.Base(context)

        @RequiresApi(Build.VERSION_CODES.M)
        override fun show(path: String, page: Int) {

            val dialog = Dialog(context)
            val binding = ImageSaveDialogBinding.inflate(dialog.layoutInflater)
            dialog.setContentView(binding.root)

            CoroutineScope(Dispatchers.Main).launch {
                binding.image.setImageBitmap(pdfRenderer.getBitmap(path, page))
            }

            binding.saveBtn.setOnClickListener {
                imageSaver.saveBitmap(binding.image)
            }

            binding.shareBtn.setOnClickListener {
                dataShare.share(imageSaver.saveBitmap(binding.image).absolutePath)
            }

            binding.textBtn.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    textCopyManager.copyToClipboard(textExtracter.extractText(path, page + 1))
                }
            }

            dialog.show()

        }
    }
}