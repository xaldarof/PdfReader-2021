package pdf.reader.simplepdfreader.data

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import pdf.reader.simplepdfreader.data.room.AppDatabase
import org.junit.Assert.*
import org.junit.Before
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.fake_test_data.PdfFilesDaoForTest
import pdf.reader.simplepdfreader.fake_test_data.PdfFilesRepositoryForTest

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PdfFilesRepositoryTest {

    private lateinit var dao: PdfFilesDaoForTest
    private lateinit var repository: PdfFilesRepositoryForTest
    private lateinit var appDatabase: AppDatabase
    private lateinit var context: Context

    private val testObject = PdfFileDb(
        "/storage", "test", favorite = false, reading = false, finished = true,
        lastPage = 0, isEverOpened = false, pageCount = 100, interesting = false, size = "10MB",
        willRead = false, lastReadTime = 0, addedTime = 0)

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        appDatabase = AppDatabase.getInstance(context)
        dao = appDatabase.testPdfFilesDao()
        repository = PdfFilesRepositoryForTest.Base(dao)
    }


    @Test
    fun check_is_database_saves_data() = runBlocking {
        val list = repository.fetchTestPdfFiles()
        assertNotNull(list)
    }

    @Test
    fun check_is_all_data_from_favorites_is_true_state() = runBlocking {
        val list = repository.fetchTestFavorites()

        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_interesting_is_true_state() = runBlocking {
        val list = repository.fetchTestInteresting()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_willRead_is_true_state() = runBlocking {
        val list = repository.fetchTestWillRead()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_finished_is_true_state() = runBlocking {
        val list = repository.fetchTestFinished()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_newPdfFiles_is_true_state() = runBlocking {
        val list = repository.fetchTestNewPdfFiles()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_data_inserted_to_database() = runBlocking {
        repository.insert(testObject)
        val list = repository.fetchTestNewPdfFiles()

        val actual = list.contains(testObject)
        assertEquals(true, actual)
    }

    @Test
    fun check_is_data_that_was_inserted_to_database_is_in_true_state() = runBlocking {
        repository.insert(testObject)
        val listFinishedFiles = repository.fetchTestFinished()
        val actual = listFinishedFiles.contains(testObject)
        assertEquals(true,actual)
    }

    @After
    fun cancelAll() {
        appDatabase.close()
    }
}
