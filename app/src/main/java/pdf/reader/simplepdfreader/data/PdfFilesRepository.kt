package pdf.reader.simplepdfreader.data

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSource
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import java.io.File

interface PdfFilesRepository {

    fun fetchPdfFiles(): Flow<List<PdfFileDb>>
    fun fetchFavorites(): Flow<List<PdfFileDb>>
    suspend fun findFilesAndInsert(dir:File)

    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)
    suspend fun updateInterestingState(dirName: String,interesting:Boolean)

    class Base(private val dataSource: PdfFilesDataSource,private val dao: PdfFilesDao) :
        PdfFilesRepository {

        override fun fetchPdfFiles(): Flow<List<PdfFileDb>> {
            return dao.fetchAllPdfFiles()
        }

        override fun fetchFavorites(): Flow<List<PdfFileDb>> {
            return dao.fetchFavorites()
        }

        override suspend fun findFilesAndInsert(dir:File) {
            dataSource.findFilesAndFetch(dir).collect {
                dao.insertPdfFile(it)
                Log.d("pdf","REP = $it")
            }
        }

        override suspend fun updateFavoriteState(dirName: String, favorite: Boolean) {
            dao.updateFavoriteState(dirName, favorite)
        }

        override suspend fun updateInterestingState(dirName: String, interesting: Boolean) {
            dao.updateInterestingState(dirName,interesting)
        }
    }
}