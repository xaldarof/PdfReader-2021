package pdf.reader.simplepdfreader.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.room.PdfFileDb

class SearchFragmentViewModel: ViewModel(),KoinComponent {

    private val pdfFilesRepository: PdfFilesRepository by inject()

    fun fetchPdfFiles() = pdfFilesRepository.fetchPdfFiles().asLiveData()

    fun fetchSearchedPdfFiles(name:String) = pdfFilesRepository.fetchSearchedPdfFiles(name).asLiveData()

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