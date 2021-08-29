package pdf.reader.simplepdfreader.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfFilesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPdfFile(pdfFiles: List<PdfFileDb>)

    @Query("SELECT * FROM pdf")
    fun fetchAllPdfFiles(): Flow<List<PdfFileDb>>


    @Query("SELECT * FROM pdf WHERE favorite = 1")
    fun fetchFavorites(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE interesting = 1")
    fun fetchInteresting(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE willRead = 1")
    fun fetchWillRead(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE finished = 1")
    fun fetchFinished(): Flow<List<PdfFileDb>>



    @Query("UPDATE pdf SET favorite = :favorite WHERE dirName = :dirName")
    suspend fun updateFavoriteState(dirName: String, favorite: Boolean)

    @Query("UPDATE pdf SET interesting = :interesting WHERE dirName = :dirName")
    suspend fun updateInterestingState(dirName: String, interesting: Boolean)

    @Query("UPDATE pdf SET willRead = :willRead WHERE dirName = :dirName")
    suspend fun updateWillReadState(dirName: String, willRead: Boolean)

    @Query("UPDATE pdf SET finished = :finished WHERE dirName = :dirName")
    suspend fun updateFinishedState(dirName: String, finished: Boolean)

}