package pdf.reader.simplepdfreader.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.core.Resource
import pdf.reader.simplepdfreader.core.Status
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import java.io.File

interface PdfFilesRepository {

    fun fetchPdfFiles(): Resource<Flow<List<PdfFileDb>>>
    fun fetchLiveDataPdfFiles(): LiveData<List<PdfFileDb>>

    fun fetchFavorites(): Resource<Flow<List<PdfFileDb>>>

    fun fetchNewPdfFiles():Resource<Flow<List<PdfFileDb>>>
    fun fetchInteresting(): Resource<Flow<List<PdfFileDb>>>
    fun fetchWillRead():Resource<Flow<List<PdfFileDb>>>
    fun fetchFinished(): Resource<Flow<List<PdfFileDb>>>
    fun fetchSearchedPdfFiles(name: String): Flow<List<PdfFileDb>>

    suspend fun findFilesAndInsert(dir: File)

    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)
    suspend fun updateInterestingState(dirName: String, interesting: Boolean)
    suspend fun updateWillReadState(dirName: String, willRead: Boolean)
    suspend fun updateFinishedState(dirName: String, finished: Boolean)

    class Base(
        private val dataSourceMobile: PdfFilesDataSourceMobile,
        private val dao: PdfFilesDao) : PdfFilesRepository {

        override fun fetchPdfFiles(): Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchAllPdfFiles(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchAllPdfFiles(),e.message)
            }
        }

        override fun fetchLiveDataPdfFiles():  LiveData<List<PdfFileDb>> {
            return dao.fetchLiveDataPdfFiles()
        }

        override fun fetchFavorites(): Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchFavorites(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchFavorites(),e.message)
            }
        }

        override fun fetchNewPdfFiles():  Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchNewPdfFiles(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchNewPdfFiles(),e.message)
            }
        }

        override fun fetchInteresting():Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchInteresting(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchInteresting(),e.message)
            }
        }

        override fun fetchWillRead():Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchWillRead(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchWillRead(),e.message)
            }
        }

        override fun fetchFinished():  Resource<Flow<List<PdfFileDb>>> {
            return try {
                Resource(Status.SUCCESS,dao.fetchFinished(),null)
            }catch (e : Exception){
                Resource(Status.ERROR,dao.fetchFinished(),e.message)
            }
        }

        override fun fetchSearchedPdfFiles(name: String): Flow<List<PdfFileDb>> {
            return dao.fetchSearchedPdfFiles(name)
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