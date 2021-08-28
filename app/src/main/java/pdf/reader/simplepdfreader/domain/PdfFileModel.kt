package pdf.reader.simplepdfreader.domain

import java.io.Serializable

data class PdfFileModel(
    var dirName: String,
    var name: String,
    var lastPage: Int,
    var pageCount: Int,
    var size: String ,
):Serializable