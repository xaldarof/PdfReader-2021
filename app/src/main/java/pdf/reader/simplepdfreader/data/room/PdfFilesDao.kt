package pdf.reader.simplepdfreader.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.CopyOnWriteArrayList

@Dao
interface PdfFilesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPdfFile(pdfFiles: CopyOnWriteArrayList<PdfFileDb>)

    @Delete
    suspend fun deletePdfFile(pdfFileDb: PdfFileDb)


    @Query("SELECT * FROM pdf ORDER BY addedTime DESC")
    fun fetchAllPdfFiles(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf ORDER BY lastReadTime DESC")
    fun fetchLiveDataPdfFiles(): LiveData<List<PdfFileDb>>


    @Query("SELECT * FROM pdf WHERE favorite = 1 ORDER BY lastReadTime DESC")
    fun fetchFavorites(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE isEverOpened = 0 ORDER BY addedTime DESC")
    fun fetchNewPdfFiles(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE willRead = 1 ORDER BY lastReadTime DESC")
    fun fetchWillRead(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE finished = 1 ORDER BY lastReadTime DESC")
    fun fetchFinished(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE interesting = 1 ORDER BY lastReadTime DESC")
    fun fetchInteresting(): Flow<List<PdfFileDb>>




    @Query("UPDATE pdf SET favorite = :favorite WHERE dirName = :dirName")
    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)

    @Query("UPDATE pdf SET interesting = :interesting WHERE dirName = :dirName")
    suspend fun updateInterestingState(dirName: String, interesting: Boolean)

    @Query("UPDATE pdf SET willRead = :willRead WHERE dirName = :dirName")
    suspend fun updateWillReadState(dirName: String, willRead: Boolean)

    @Query("UPDATE pdf SET finished = :finished WHERE dirName = :dirName")
    suspend fun updateFinishedState(dirName: String, finished: Boolean)




    @Query("UPDATE pdf SET lastPage = :lastPage WHERE dirName = :dirName")
    suspend fun updateLastPage(dirName: String, lastPage:Int)

    @Query("UPDATE pdf SET pageCount = :pageCount WHERE dirName = :dirName")
    suspend fun updatePageCount(dirName: String, pageCount:Int)

    @Query("UPDATE pdf SET lastReadTime = :lastReadTime WHERE dirName = :dirName")
    suspend fun updateLastReadTime(dirName: String, lastReadTime:Long)

    @Query("UPDATE pdf SET isEverOpened = :isEverOpened WHERE dirName = :dirName")
    suspend fun updateIsEverOpened(dirName: String, isEverOpened:Boolean)

    @Query("UPDATE pdf SET name = :name WHERE dirName = :dirName")
    suspend fun updateName(dirName: String, name:String)

    @Query("UPDATE pdf SET dirName = :newPath WHERE dirName = :dirName")
    suspend fun updatePath(dirName: String, newPath:String)


}