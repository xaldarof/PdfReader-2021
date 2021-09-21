package pdf.reader.simplepdfreader.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookDb(
    val title:String,
    val author:String,
    @PrimaryKey
    val isbn:String,

    val addedTime:String
)
