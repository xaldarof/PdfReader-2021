package pdf.reader.simplepdfreader.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.ReadingFileRepository
import pdf.reader.simplepdfreader.data.room.PdfFilesDao

class ReadingActivityViewModel : ViewModel(),KoinComponent {

    private val pdfFilesDao:PdfFilesDao by inject()
    private val readingFileRepository = ReadingFileRepository.Base(pdfFilesDao)

    fun updateLastPage(dirName:String,lastPage:Int) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updateLastPage(dirName,lastPage)
    }

    fun updatePageCount(dirName:String,pageCount:Int) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updatePageCount(dirName,pageCount)
    }

}