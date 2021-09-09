package pdf.reader.simplepdfreader.fake_test_data

import pdf.reader.simplepdfreader.data.room.PdfFileDb

interface PdfFilesRepositoryForTest {

    suspend fun fetchTestPdfFiles():List<PdfFileDb>
    suspend fun fetchTestFavorites(): List<PdfFileDb>
    suspend fun fetchTestNewPdfFiles(): List<PdfFileDb>
    suspend fun fetchTestInteresting(): List<PdfFileDb>
    suspend fun fetchTestWillRead(): List<PdfFileDb>
    suspend fun fetchTestFinished(): List<PdfFileDb>

    suspend fun insert(pdfFileDb: PdfFileDb)

    class Base(private val dao: PdfFilesDaoForTest): PdfFilesRepositoryForTest {
        override suspend fun fetchTestPdfFiles(): List<PdfFileDb> {
            return dao.testFetchAllPdfFiles()
        }

        override suspend fun fetchTestFavorites(): List<PdfFileDb> {
            return dao.testFetchFavorites()
        }

        override suspend fun fetchTestNewPdfFiles(): List<PdfFileDb> {
            return dao.testFetchNewPdfFiles()
        }

        override suspend fun fetchTestInteresting(): List<PdfFileDb> {
            return dao.testFetchInteresting()
        }

        override suspend fun fetchTestWillRead(): List<PdfFileDb> {
            return dao.testFetchWillRead()
        }

        override suspend fun fetchTestFinished(): List<PdfFileDb> {
            return dao.testFetchFinished()
        }

        override suspend fun insert(pdfFileDb: PdfFileDb) {
            dao.insert(pdfFileDb)
        }
    }
}
