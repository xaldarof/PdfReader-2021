package pdf.reader.simplepdfreader.data.cloud.api

import java.io.Serializable

data class Book(
    val author: String,
    val title: String,
    val publisher: String,
    val publisherData: String,
    val desc: String,
    val image: String,
    val webReadLink: String,
    val buyLink:String,
    val pdf:String
) : Serializable