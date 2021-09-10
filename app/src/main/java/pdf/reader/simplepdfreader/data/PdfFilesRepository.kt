package pdf.reader.simplepdfreader.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import java.io.File

interface PdfFilesRepository {

    fun fetchPdfFiles(): Flow<List<PdfFileDb>>

    fun fetchFavorites(): Flow<List<PdfFileDb>>

    suspend fun fetchDataForCount():LiveData<List<PdfFileDb>>

    fun fetchNewPdfFiles():Flow<List<PdfFileDb>>
    fun fetchInteresting(): Flow<List<PdfFileDb>>
    fun fetchWillRead():Flow<List<PdfFileDb>>
    fun fetchFinished(): Flow<List<PdfFileDb>>

    suspend fun findFilesAndInsert(dir: File)

    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)
    suspend fun updateInterestingState(dirName: String, interesting: Boolean)
    suspend fun updateWillReadState(dirName: String, willRead: Boolean)
    suspend fun updateFinishedState(dirName: String, finished: Boolean)

    class Base(private val dataSourceMobile: PdfFilesDataSourceMobile,
               private val dao: PdfFilesDao) : PdfFilesRepository {

        override fun fetchPdfFiles(): Flow<List<PdfFileDb>> {
            return dao.fetchAllPdfFiles()
        }

        override fun fetchFavorites(): Flow<List<PdfFileDb>> {
            return dao.fetchFavorites()
        }

        override suspend fun fetchDataForCount(): LiveData<List<PdfFileDb>> {
            return dao.fetchLiveDataPdfFiles()
        }

        override fun fetchNewPdfFiles(): Flow<List<PdfFileDb>> {
            return dao.fetchNewPdfFiles()
        }

        override fun fetchInteresting(): Flow<List<PdfFileDb>> {
           return dao.fetchInteresting()
        }

        override fun fetchWillRead():Flow<List<PdfFileDb>> {
           return dao.fetchWillRead()
        }

        override fun fetchFinished(): Flow<List<PdfFileDb>> {
            return dao.fetchFinished()
        }
        override suspend fun findFilesAndInsert(dir: File) {
            CoroutineScope(Dispatchers.IO).launch {
                dataSourceMobile.findFilesAndFetch(dir).collect {
                    val newList = ArrayList<PdfFileDb>()
                    newList.addAll(it)
                    dao.insertPdfFile(newList)
                }
            }
        }

        override suspend fun updateFavoriteState(dirName: String, favorite: Boolean) {
            dao.updateFavoriteState(dirName, favorite)
        }

        override suspend fun updateInterestingState(dirName: String, interesting: Boolean) {
            dao.updateInterestingState(dirName, interesting)
        }

        override suspend fun updateWillReadState(dirName: String, willRead: Boolean) {
            dao.updateWillReadState(dirName, willRead)
        }

        override suspend fun updateFinishedState(dirName: String, finished: Boolean) {
            dao.updateFinishedState(dirName, finished)
        }
    }

}