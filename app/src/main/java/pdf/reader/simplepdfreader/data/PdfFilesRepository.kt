package pdf.reader.simplepdfreader.data

import kotlinx.coroutines.flow.Flow
import pdf.reader.simplepdfreader.data.cache.PdfFilesCacheDataSource
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import java.io.File

interface PdfFilesRepository {

    fun fetchPdfFiles(): Flow<List<PdfFileDb>>
    fun fetchFavorites(): Flow<List<PdfFileDb>>
    suspend fun findFilesAndInsert(dir:File)

    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)
    suspend fun updateInterestingState(dirName: String,interesting:Boolean)
    suspend fun updateWillReadState(dirName: String, willRead: Boolean)
    suspend fun updateFinishedState(dirName: String, finished: Boolean)

    class Base(private val cacheDataSource: PdfFilesCacheDataSource) :
        PdfFilesRepository {

        override fun fetchPdfFiles(): Flow<List<PdfFileDb>> {
            return cacheDataSource.fetchPdfFiles()
        }

        override fun fetchFavorites(): Flow<List<PdfFileDb>> {
            return cacheDataSource.fetchPdfFiles()
        }

        override suspend fun findFilesAndInsert(dir:File) {
           cacheDataSource.findFilesAndInsert(dir)
        }

        override suspend fun updateFavoriteState(dirName: String, favorite: Boolean) {
            cacheDataSource.updateFavoriteState(dirName, favorite)
        }

        override suspend fun updateInterestingState(dirName: String, interesting: Boolean) {
            cacheDataSource.updateInterestingState(dirName,interesting)
        }

        override suspend fun updateWillReadState(dirName: String, willRead: Boolean) {
            cacheDataSource.updateWillReadState(dirName,willRead)
        }

        override suspend fun updateFinishedState(dirName: String, finished: Boolean) {
            cacheDataSource.updateFinishedState(dirName,finished)
        }
    }
}