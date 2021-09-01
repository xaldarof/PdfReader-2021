package pdf.reader.simplepdfreader.data.room

interface PdfFilesFromFolderRepository {


    suspend fun savePdfFile(pdfFileDb: PdfFileDb)

    class Base(private val dao: PdfFilesDao) : PdfFilesFromFolderRepository{
        override suspend fun savePdfFile(pdfFileDb: PdfFileDb) {
            dao.insertSinglePdfFile(pdfFileDb)
        }
    }
}