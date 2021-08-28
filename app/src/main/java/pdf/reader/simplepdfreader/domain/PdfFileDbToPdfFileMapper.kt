package pdf.reader.simplepdfreader.domain

import pdf.reader.simplepdfreader.data.room.PdfFileDb

interface PdfFileDbToPdfFileMapper<T,M> {

    fun map(data:T):M

    class Base : PdfFileDbToPdfFileMapper<PdfFileDb,PdfFileModel>{
        override fun map(data: PdfFileDb): PdfFileModel {
            return PdfFileModel(data.dirName,data.name,data.lastPage,data.pageCount,data.size)
        }

    }

}