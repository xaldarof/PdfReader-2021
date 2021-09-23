package pdf.reader.simplepdfreader.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "books")
data class BookDb(
    val title:String,
    val author:String,
    @PrimaryKey
    val isbn:String,

    val addedTime:String
)
