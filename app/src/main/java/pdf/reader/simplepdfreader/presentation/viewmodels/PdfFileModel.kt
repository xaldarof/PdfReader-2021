package pdf.reader.simplepdfreader.presentation.viewmodels

import java.io.Serializable

data class PdfFileModel(
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
    var willRead: Boolean = false,
    var lastReadTime:Long = 0,
    var addedTime:Long = 0
):Serializable