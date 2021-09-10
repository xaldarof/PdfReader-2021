package pdf.reader.simplepdfreader.fake_test_data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pdf.reader.simplepdfreader.data.room.PdfFileDb

@Dao
interface FakePdfFilesDao {

    @Insert
    suspend fun insert(pdfFileDb: PdfFileDb)

    @Delete
    suspend fun delete(pdfFileDb: PdfFileDb)

    @Query("SELECT * FROM pdf")
    suspend fun testFetchAllPdfFiles(): List<PdfFileDb>

    @Query("SELECT * FROM pdf WHERE favorite = 1 ORDER BY lastReadTime DESC")
    suspend fun testFetchFavorites(): List<PdfFileDb>

    @Query("SELECT * FROM pdf WHERE isEverOpened = 0 ORDER BY addedTime DESC")
    suspend fun testFetchNewPdfFiles(): List<PdfFileDb>

    @Query("SELECT * FROM pdf WHERE willRead = 1 ORDER BY lastReadTime DESC")
    suspend fun testFetchWillRead(): List<PdfFileDb>

    @Query("SELECT * FROM pdf WHERE finished = 1 ORDER BY lastReadTime DESC")
    suspend fun testFetchFinished(): List<PdfFileDb>

    @Query("SELECT * FROM pdf WHERE interesting = 1 ORDER BY lastReadTime DESC")
    suspend fun testFetchInteresting(): List<PdfFileDb>


}