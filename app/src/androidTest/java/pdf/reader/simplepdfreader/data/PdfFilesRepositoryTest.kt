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
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesDao
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesRepository

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PdfFilesRepositoryTest {

    private lateinit var daoFake: FakePdfFilesDao
    private lateinit var repositoryFake: FakePdfFilesRepository
    private lateinit var appDatabase: AppDatabase
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        appDatabase = AppDatabase.getInstance(context)
        daoFake = appDatabase.testPdfFilesDao()
        repositoryFake = FakePdfFilesRepository.Base(daoFake)
    }


    @Test
    fun check_is_database_saves_data() = runBlocking {
        val list = repositoryFake.fetchTestPdfFiles()
        Log.d("pos","$list")
        assertNotNull(list)
    }

    @Test
    fun check_is_all_data_from_favorites_is_true_state() = runBlocking {
        val list = repositoryFake.fetchTestFavorites()

        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_interesting_is_true_state() = runBlocking {
        val list = repositoryFake.fetchTestInteresting()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_willRead_is_true_state() = runBlocking {
        val list = repositoryFake.fetchTestWillRead()
        for (i in list.indices) {
            assertEquals(true, list[i].favorite)
        }
    }

    @Test
    fun check_is_all_data_from_finished_is_true_state() = runBlocking {
        val list = repositoryFake.fetchTestFinished()
        for (i in list.indices) {
            assertEquals(true, list[i].finished)
        }
    }

    @Test
    fun check_is_all_data_from_newPdfFiles_is_true_state() = runBlocking {
        val list = repositoryFake.fetchTestNewPdfFiles()
        for (i in list.indices) {
            assertEquals(true, list[i].isEverOpened)
        }
    }

    @Test
    fun check_is_data_name_changed() = runBlocking {
        val TEST_NAME = "UNIT TEST DATA"
        val TEST_PATH = "/storage/emulated/0/Download/cryptography_with_python_tutorial.pdf"
        repositoryFake.updateName(TEST_PATH,TEST_NAME)
        val list = repositoryFake.fetchTestPdfFiles()

        var actual = false
        list.forEach {
            if (it.name==TEST_NAME) actual = true
        }
        assertEquals(true,actual)

    }


    @After
    fun cancelAll() {
        appDatabase.close()
    }
}
