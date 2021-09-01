package pdf.reader.simplepdfreader.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import pdf.reader.simplepdfreader.data.room.PdfFilesFromFolderRepository

class MainActivityViewModel : ViewModel(),KoinComponent {

    private val pdfFilesFromFolderRepository : PdfFilesFromFolderRepository by inject()

    fun insertSinglePdfFile(pdfFileDb: PdfFileDb) = CoroutineScope(Dispatchers.IO).launch {
        pdfFilesFromFolderRepository.savePdfFile(pdfFileDb)
    }

}