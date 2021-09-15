package pdf.reader.simplepdfreader.data.cache

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


interface PdfFilesDataSourceMobile {

    suspend fun findFilesAndFetch(dir: File): Flow<List<PdfFileDb>>

    class Base : PdfFilesDataSourceMobile {

        private val entities = ArrayList<PdfFileDb>()

        override suspend fun findFilesAndFetch(dir: File) : Flow<List<PdfFileDb>> {
            val list = ArrayList<PdfFileDb>()
            coroutineScope {
                val fileList = dir.listFiles()

                if (fileList != null) {
                    for (i in fileList.indices) {
                        val date = Date().time
                        if (fileList[i].isDirectory) {
                            findFilesAndFetch(fileList[i])
                        } else {
                            if (fileList[i].name.endsWith(".pdf")) {
                                list.add(
                                    PdfFileDb(
                                        fileList[i].toString(),
                                        fileList[i].name.toString(),
                                        favorite = false,
                                        reading = false,
                                        finished = false,
                                        lastPage = 0,
                                        isEverOpened = false,
                                        pageCount = 0,
                                        interesting = false,
                                        size = getSize(fileList[i].length()),
                                        willRead = false,
                                        lastReadTime = 0,
                                        addedTime = date
                                    )
                                )
                            }
                        }
                    }
                    entities.addAll(list)
                }
            }
            return flow {
                emit(entities)
                entities.clear() }
        }

       private fun getSize(size: Long): String {
            val df = DecimalFormat("0.00")
            val sizeKb = 1024.0f
            val sizeMb = sizeKb * sizeKb
            val sizeGb = sizeMb * sizeKb
            val sizeTerra = sizeGb * sizeKb
           return when {
               size < sizeMb -> df.format(size / sizeKb)
                   .toString() + " КБ"
               size < sizeGb -> df.format(size / sizeMb)
                   .toString() + " МБ"
               size < sizeTerra -> df.format(size / sizeGb)
                   .toString() + " ГБ"
               else -> ""
           }
       }
    }
}



