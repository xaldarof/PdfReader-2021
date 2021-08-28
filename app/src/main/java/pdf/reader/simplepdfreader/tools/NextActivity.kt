package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.content.Intent
import pdf.reader.simplepdfreader.domain.PdfFileModel
import pdf.reader.simplepdfreader.presentation.ReadingActivity

interface NextActivity {

    fun startActivity(pdfFileModel: PdfFileModel)

    class Base(private val context: Context) : NextActivity {
        override fun startActivity(pdfFileModel: PdfFileModel) {
            val intent = Intent(context,ReadingActivity::class.java)
            intent.putExtra("pdf",pdfFileModel)
            context.startActivity(intent)
        }

    }
}