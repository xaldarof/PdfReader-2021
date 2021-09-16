package pdf.reader.simplepdfreader.data.data_source

import android.content.Context
import kotlinx.coroutines.flow.Flow
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import java.io.File
import kotlinx.coroutines.flow.flow


interface PdfFilesDataSourceSD {

    suspend fun findFilesAndFetch(dir: File,context: Context): Flow<List<PdfFileDb>>

    class Base: PdfFilesDataSourceSD {
        override suspend fun findFilesAndFetch(dir: File,context: Context): Flow<List<PdfFileDb>> {


            return flow {  }
        }
    }
}