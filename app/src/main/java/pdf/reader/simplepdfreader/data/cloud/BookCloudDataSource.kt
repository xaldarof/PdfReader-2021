package pdf.reader.simplepdfreader.data.cloud

import pdf.reader.simplepdfreader.data.cloud.api.ApiService
import pdf.reader.simplepdfreader.data.cloud.api.models.BookCloud

interface BookCloudDataSource {

    suspend fun fetchBookInfo(isbn:String):BookCloud

    class Base(private val apiService: ApiService):BookCloudDataSource{
        override suspend fun fetchBookInfo(isbn: String): BookCloud {
            return apiService.fetchBookInfo(isbn)
        }

    }
}