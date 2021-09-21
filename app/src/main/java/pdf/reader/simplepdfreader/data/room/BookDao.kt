package pdf.reader.simplepdfreader.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBook(bookDb: BookDb)

    @Query("SELECT * FROM books ORDER BY addedTime DESC")
    fun fetchBooks():Flow<List<BookDb>>

    @Delete
    fun deleteBook(bookDb: BookDb)

}