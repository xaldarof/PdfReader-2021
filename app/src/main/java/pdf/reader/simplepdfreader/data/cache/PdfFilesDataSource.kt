package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.data.room.PdfFileDb
import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


interface PdfFilesDataSource {

    suspend fun findFilesAndFetch(dir: File): Flow<List<PdfFileDb>>

    class Base(private val context: Context) : PdfFilesDataSource {

        private val entities = ArrayList<PdfFileDb>()

        override suspend fun findFilesAndFetch(dir: File) : Flow<List<PdfFileDb>> {
            val list = ArrayList<PdfFileDb>()
            coroutineScope {
                val pdfPattern = ".pdf";
                val fileList = dir.listFiles();

                if (fileList != null) {
                    for (i in fileList.indices) {
                        if (fileList[i].isDirectory) {
                            findFilesAndFetch(fileList[i])
                        } else {
                            if (fileList[i].name.endsWith(pdfPattern)) {
                                list.add(
                                    PdfFileDb(
                                        fileList[i].absolutePath.toString(),
                                        fileList[i].name.toString(),
                                        favorite = false,
                                        reading = false,
                                        finished = false,
                                        lastPage = 0,
                                        isEverOpened = false,
                                        pageCount = 0,
                                        interesting = false,
                                        size = getSize(fileList[i].length()),
                                        willRead = false
                                    )
                                )
                            }
                        }
                    }
                    entities.addAll(list)
                }
            }
            return flow { emit(entities) }
        }
       private fun getSize(size: Long): String {
            val df = DecimalFormat("0.00")
            val sizeKb = 1024.0f
            val sizeMb = sizeKb * sizeKb
            val sizeGb = sizeMb * sizeKb
            val sizeTerra = sizeGb * sizeKb
            if (size < sizeMb) return df.format(size / sizeKb)
                .toString() + " Kb" else if (size < sizeGb) return df.format(size / sizeMb)
                .toString() + " Mb" else if (size < sizeTerra) return df.format(size / sizeGb)
                .toString() + " Gb"
            return ""
        }
    }
}



