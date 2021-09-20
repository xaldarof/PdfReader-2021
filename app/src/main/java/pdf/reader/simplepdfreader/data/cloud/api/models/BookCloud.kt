package pdf.reader.simplepdfreader.data.cloud.api.models

import pdf.reader.simplepdfreader.data.cloud.api.Book

interface BookCloudToBookMapper{
    fun mapToBook():Book
}

data class BookCloud(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int) : BookCloudToBookMapper{

    override fun mapToBook():Book{
        return Book(
            items[0].volumeInfo.authors[0],
            items[0].volumeInfo.title,
            items[0].volumeInfo.publisher,
            items[0].volumeInfo.publishedDate,
            items[0].volumeInfo.description,
            items[0].volumeInfo.imageLinks.thumbnail,
            items[0].accessInfo.webReaderLink,
            items[0].saleInfo.buyLink,
            items[0].accessInfo.pdf.acsTokenLink
        )
    }
}