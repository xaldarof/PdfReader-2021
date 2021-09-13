package pdf.reader.simplepdfreader.fake_test_data

import pdf.reader.simplepdfreader.data.room.PdfFileDb

interface FakePdfFilesRepository {

    suspend fun fetchTestPdfFiles():List<PdfFileDb>
    suspend fun fetchTestFavorites(): List<PdfFileDb>
    suspend fun fetchTestNewPdfFiles(): List<PdfFileDb>
    suspend fun fetchTestInteresting(): List<PdfFileDb>
    suspend fun fetchTestWillRead(): List<PdfFileDb>
    suspend fun fetchTestFinished(): List<PdfFileDb>

    suspend fun updateName(dirName:String,name:String)

    suspend fun insert(pdfFileDb: PdfFileDb)

    class Base(private val daoFake: FakePdfFilesDao): FakePdfFilesRepository {
        override suspend fun fetchTestPdfFiles(): List<PdfFileDb> {
            return daoFake.testFetchAllPdfFiles()
        }

        override suspend fun fetchTestFavorites(): List<PdfFileDb> {
            return daoFake.testFetchFavorites()
        }

        override suspend fun fetchTestNewPdfFiles(): List<PdfFileDb> {
            return daoFake.testFetchNewPdfFiles()
        }

        override suspend fun fetchTestInteresting(): List<PdfFileDb> {
            return daoFake.testFetchInteresting()
        }

        override suspend fun fetchTestWillRead(): List<PdfFileDb> {
            return daoFake.testFetchWillRead()
        }

        override suspend fun fetchTestFinished(): List<PdfFileDb> {
            return daoFake.testFetchFinished()
        }

        override suspend fun updateName(dirName: String, name: String) {
            daoFake.updateName(dirName,name)
        }

        override suspend fun insert(pdfFileDb: PdfFileDb) {
            daoFake.insert(pdfFileDb)
        }
    }
}
