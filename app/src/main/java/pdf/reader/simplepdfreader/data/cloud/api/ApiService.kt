package pdf.reader.simplepdfreader.data.cloud.api

import pdf.reader.simplepdfreader.data.cloud.api.models.BookCloud
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("volumes?")
    suspend fun fetchBookInfo(@Query("q") isbn:String): BookCloud

}