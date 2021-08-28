package pdf.reader.simplepdfreader.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdf")
data class PdfFileDb(

    @PrimaryKey
    var dirName: String = "",

    var name: String = "",
    var favorite: Boolean = false,
    var reading: Boolean = false,
    var finished: Boolean = false,
    var lastPage: Int = 0,
    var isEverOpened: Boolean = false,
    var pageCount: Int = 0,
    var interesting: Boolean = false,
    var size: String = "",
    var willRead: Boolean = false

)

