package pdf.reader.simplepdfreader.data.core

import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.data.room.PdfFilesDao

interface ReadingFileRepository {

    suspend fun updateLastPage(dirName:String,lastPage:Int)
    suspend fun updatePageCount(dirName:String,pageCount:Int)
    suspend fun updateLasReadTime(dirName: String,lastReadTime:Long)
    suspend fun updateIsEveOpened(dirName: String,isEverOpened:Boolean)

    suspend fun deletePdfFile(pdfFileDb: PdfFileDb)

    class Base(private val pdfFilesDao: PdfFilesDao) : ReadingFileRepository {
        override suspend fun updateLastPage(dirName:String,lastPage:Int) {
            pdfFilesDao.updateLastPage(dirName,lastPage)
        }

        override suspend fun updatePageCount(dirName: String, pageCount: Int) {
            pdfFilesDao.updatePageCount(dirName,pageCount)
        }

        override suspend fun updateLasReadTime(dirName: String, lastReadTime: Long) {
            pdfFilesDao.updateLastReadTime(dirName,lastReadTime)
        }

        override suspend fun updateIsEveOpened(dirName: String, isEverOpened: Boolean) {
            pdfFilesDao.updateIsEverOpened(dirName,isEverOpened)
        }

        override suspend fun deletePdfFile(pdfFileDb: PdfFileDb) {
            pdfFilesDao.deletePdfFile(pdfFileDb)
        }
    }
}