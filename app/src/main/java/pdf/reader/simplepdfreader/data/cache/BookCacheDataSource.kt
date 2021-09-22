package pdf.reader.simplepdfreader.data.cache

import kotlinx.coroutines.flow.Flow
import pdf.reader.simplepdfreader.data.room.BookDao
import pdf.reader.simplepdfreader.data.room.BookDb

interface BookCacheDataSource {

    fun fetchBooks():Flow<List<BookDb>>

    suspend fun insertBook(bookDb: BookDb)
    suspend fun deleteBook(bookDb: BookDb)

    class Base(private val bookDao: BookDao) : BookCacheDataSource{
        override fun fetchBooks(): Flow<List<BookDb>> {
            return bookDao.fetchBooks()
        }

        override suspend fun insertBook(bookDb: BookDb) {
            bookDao.insertBook(bookDb)
        }

        override suspend fun deleteBook(bookDb: BookDb) {
            bookDao.deleteBook(bookDb)
        }

    }
}