package pdf.reader.simplepdfreader.data.data_source

import kotlinx.coroutines.coroutineScope
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pdf.reader.simplepdfreader.tools.ByteToMbConverter
import java.io.File
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList


interface PdfFilesDataSourceMobile {

    suspend fun findFilesAndFetch(dir: File): Flow<CopyOnWriteArrayList<PdfFileDb>>

    class Base(private val byteToMbConverter : ByteToMbConverter) : PdfFilesDataSourceMobile {

        private val entities = CopyOnWriteArrayList<PdfFileDb>()

        override suspend fun findFilesAndFetch(dir: File) : Flow<CopyOnWriteArrayList<PdfFileDb>> {
            val list = CopyOnWriteArrayList<PdfFileDb>()
            coroutineScope {
                val fileList = dir.listFiles()

                if (fileList != null) {
                    for (i in fileList.indices) {
                        val date = Date().time
                        if (fileList[i].isDirectory) {
                            findFilesAndFetch(fileList[i])
                        } else {
                            if (fileList[i].name.endsWith(".pdf")) {
                                list.add(PdfFileDb(
                                        fileList[i].toString(), fileList[i].name.toString(), favorite = false,
                                        reading = false, finished = false, lastPage = 0, isEverOpened = false,
                                        pageCount = 0, interesting = false, size = byteToMbConverter.convert(fileList[i].length()),
                                        willRead = false, lastReadTime = 0, addedTime = date
                                    )
                                )
                            }
                        }
                    }
                    entities.addAll(list)
                }
            }
            return flow { emit(entities)
                    entities.clear() }
            }
        }
    }




