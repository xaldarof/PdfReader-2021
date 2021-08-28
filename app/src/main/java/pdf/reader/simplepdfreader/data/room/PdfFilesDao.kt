package pdf.reader.simplepdfreader.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfFilesDao {

    @Query("SELECT * FROM pdf")
    fun fetchAllPdfFiles(): Flow<List<PdfFileDb>>

    @Query("SELECT * FROM pdf WHERE favorite = 'true'")
    fun fetchFavorites(): Flow<List<PdfFileDb>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPdfFile(pdfFiles: List<PdfFileDb>)


    @Query("UPDATE pdf SET favorite = :favorite WHERE dirName = :dirName")
    suspend fun updateFavoriteState(dirName: String,favorite:Boolean)

    @Query("UPDATE pdf SET interesting = :interesting WHERE dirName = :dirName")
    suspend fun updateInterestingState(dirName: String,interesting:Boolean)

}