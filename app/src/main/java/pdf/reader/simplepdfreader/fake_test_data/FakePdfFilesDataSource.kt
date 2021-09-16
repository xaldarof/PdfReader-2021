package pdf.reader.simplepdfreader.fake_test_data

import kotlinx.coroutines.flow.collect
import pdf.reader.simplepdfreader.data.data_source.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import java.io.File

class FakePdfFilesDataSource(private val pdfFilesDataSourceMobile: PdfFilesDataSourceMobile) {

    suspend fun findFilesAndFetch(dir: File): List<PdfFileDb> {
        val list = ArrayList<PdfFileDb>()
        pdfFilesDataSourceMobile.findFilesAndFetch(dir).collect {
            list.addAll(it)
        }
        return list
    }

}