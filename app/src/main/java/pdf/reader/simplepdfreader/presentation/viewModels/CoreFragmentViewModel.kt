package pdf.reader.simplepdfreader.presentation.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSource
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import java.io.File

class CoreFragmentViewModel(context: Context) : ViewModel(), KoinComponent {

    private val dataSource = PdfFilesDataSource.Base(context)
    private val dao: PdfFilesDao by inject()
    private val pdfFilesRepository = PdfFilesRepository.Base(dataSource, dao)

    fun fetchPdfFiles() = pdfFilesRepository.fetchPdfFiles().asLiveData()

    fun findPdfFilesAndInsert(dir: File) = viewModelScope.launch {
        pdfFilesRepository.findFilesAndInsert(dir)
    }

    fun updateFavoriteState(pdfFileDb: PdfFileDb) = viewModelScope.launch {
        pdfFilesRepository.updateFavoriteState(pdfFileDb.dirName, !pdfFileDb.favorite)

    }

    fun updateInterestingState(pdfFileDb: PdfFileDb) = viewModelScope.launch {
        pdfFilesRepository.updateInterestingState(pdfFileDb.dirName, !pdfFileDb.interesting)
    }

}