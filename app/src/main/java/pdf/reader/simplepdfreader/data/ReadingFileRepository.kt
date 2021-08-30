package pdf.reader.simplepdfreader.data

import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao

interface ReadingFileRepository {

    suspend fun updateLastPage(dirName:String,lastPage:Int)
    suspend fun updatePageCount(dirName:String,pageCount:Int)

    class Base(private val pdfFilesDao: PdfFilesDao) : ReadingFileRepository{
        override suspend fun updateLastPage(dirName:String,lastPage:Int) {
            pdfFilesDao.updateLastPage(dirName,lastPage)
        }

        override suspend fun updatePageCount(dirName: String, pageCount: Int) {
            pdfFilesDao.updatePageCount(dirName,pageCount)
        }
    }
}