package pdf.reader.simplepdfreader.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import pdf.reader.simplepdfreader.data.cloud.BookRepository

class HistoryFragmentViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun fetchBooksCache() = bookRepository.fetchBookCacheInfo().asLiveData()

}