package pdf.reader.simplepdfreader.domain

import pdf.reader.simplepdfreader.data.room.PdfFileDb

interface PdfFileToPdfFileDbMapper<T,M> {

    fun map(data:T):M

    class Base : PdfFileDbToPdfFileMapper<PdfFileModel,PdfFileDb>{
        override fun map(data: PdfFileModel): PdfFileDb {
            return PdfFileDb(data.dirName,data.name)
        }
    }
}