package pdf.reader.simplepdfreader.data.cloud.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pdf.reader.simplepdfreader.data.cloud.api.models.BookModel

class ApiServiceImpl(private val apiService: ApiService): ApiServiceHelper{
    override suspend fun fetchBookInfo(isbn: String): Flow<BookModel> {
        return flow { apiService.fetchBookInfo(isbn) }
    }
}