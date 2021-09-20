package pdf.reader.simplepdfreader.data.cloud

import kotlinx.coroutines.flow.Flow
import pdf.reader.simplepdfreader.core.RequestResult
import pdf.reader.simplepdfreader.core.Status
import pdf.reader.simplepdfreader.data.cache.BookCacheDataSource
import pdf.reader.simplepdfreader.data.cloud.api.Book
import pdf.reader.simplepdfreader.data.room.BookDb

interface BookRepository {

    suspend fun fetchBookCloudInfo(isbn:String):RequestResult<Book>
    fun fetchBookCacheInfo():Flow<List<BookDb>>

    suspend fun insertBookCache(bookDb: BookDb)

    class Base(private val cloudDataSource: BookCloudDataSource,
               private val cacheDataSource: BookCacheDataSource) : BookRepository {

        override suspend fun fetchBookCloudInfo(isbn:String):RequestResult<Book>{
            try {
                if (cloudDataSource.fetchBookInfo(isbn).items.isNotEmpty()) {
                    return RequestResult(Status.SUCCESS,cloudDataSource.fetchBookInfo(isbn).mapToBook())
                }
            }catch (e:Exception){
                return RequestResult(Status.ERROR,null, "${e.message}")
            }
            return RequestResult(Status.LOADING, cloudDataSource.fetchBookInfo(isbn).mapToBook())
        }

        override fun fetchBookCacheInfo(): Flow<List<BookDb>> {
            return cacheDataSource.fetchBooks()
        }

        override suspend fun insertBookCache(bookDb: BookDb) {
            cacheDataSource.insertBook(bookDb)
        }
    }

}