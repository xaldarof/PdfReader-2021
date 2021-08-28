package pdf.reader.simplepdfreader.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.cache.PdfFilesCacheDataSource
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSource
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import java.io.File

class CoreFragmentViewModel(context: Context) : ViewModel(), KoinComponent {

    private val dataSource = PdfFilesDataSource.Base(context)
    private val dao: PdfFilesDao by inject()
    private val cacheDataSource = PdfFilesCacheDataSource.Base(dataSource,dao)
    private val pdfFilesRepository = PdfFilesRepository.Base(cacheDataSource)

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

    fun updateWillReadState(pdfFileDb: PdfFileDb) = viewModelScope.launch {
        pdfFilesRepository.updateWillReadState(pdfFileDb.dirName,!pdfFileDb.willRead)
    }
    fun updateFinishedState(pdfFileDb: PdfFileDb) = viewModelScope.launch {
        pdfFilesRepository.updateFinishedState(pdfFileDb.dirName,!pdfFileDb.finished)
    }

}