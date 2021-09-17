package pdf.reader.simplepdfreader.data.cloud.api.models

data class BookModel(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)