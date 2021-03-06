package pdf.reader.simplepdfreader.presentation.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import java.io.File

class CoreFragmentViewModel(private val pdfFilesRepository: PdfFilesRepository) : ViewModel() {

    fun fetchPdfFiles() = pdfFilesRepository.fetchPdfFiles().asLiveData()

    fun findPdfFilesAndInsert(dir: File) = CoroutineScope(Dispatchers.IO).launch {
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

    fun delete(pdfFileDb: PdfFileDb) = viewModelScope.launch {
        pdfFilesRepository.deletePdfFile(pdfFileDb)
    }
}