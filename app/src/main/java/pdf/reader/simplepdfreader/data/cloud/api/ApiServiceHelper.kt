package pdf.reader.simplepdfreader.data.cloud.api

import kotlinx.coroutines.flow.Flow
import pdf.reader.simplepdfreader.data.cloud.api.models.BookModel

interface ApiServiceHelper {

    suspend fun fetchBookInfo(isbn:String):Flow<BookModel>
}