package pdf.reader.simplepdfreader.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.core.RequestResult
import pdf.reader.simplepdfreader.data.cloud.BookRepository
import pdf.reader.simplepdfreader.data.cloud.api.Book
import pdf.reader.simplepdfreader.data.room.BookDb

interface Requests {
   suspend fun fetchBooks(isbn: String):RequestResult<Book>

   suspend fun insertBookCache(bookDb: BookDb)
   fun fetchBookCache():Flow<List<BookDb>>
}

class SearchActivityViewModel(private val bookRepository: BookRepository) : ViewModel(), Requests {

    override suspend fun fetchBooks(isbn: String) : RequestResult<Book> {
        return bookRepository.fetchBookCloudInfo(isbn)
    }

    override suspend fun insertBookCache(bookDb: BookDb) {
        bookRepository.insertBookCache(bookDb)
    }

    override fun fetchBookCache(): Flow<List<BookDb>> {
        return bookRepository.fetchBookCacheInfo()
    }


}