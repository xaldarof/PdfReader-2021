package pdf.reader.simplepdfreader.domain

import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.presentation.viewmodels.PdfFileModel

interface PdfFileToPdfFileDbMapper<T,M> {

    fun map(data:T):M

    class Base : PdfFileDbToPdfFileMapper<PdfFileModel,PdfFileDb>{
        override fun map(data: PdfFileModel): PdfFileDb {
            return PdfFileDb(data.dirName,data.name,data.favorite,data.reading,
                data.finished,data.lastPage,data.isEverOpened,data.pageCount,data.interesting,
                data.size,data.willRead,data.lastReadTime,data.addedTime)
        }
    }
}