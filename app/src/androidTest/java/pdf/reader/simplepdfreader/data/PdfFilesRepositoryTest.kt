package pdf.reader.simplepdfreader.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import org.junit.Assert.*
import org.junit.Before
import pdf.reader.simplepdfreader.data.room.PdfFileDb

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PdfFilesRepositoryTest {

    private lateinit var dataSource: PdfFilesDataSourceMobile
    private lateinit var dao: PdfFilesDao
    private lateinit var repository: PdfFilesRepository
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataSource = PdfFilesDataSourceMobile.Base(context)
        appDatabase = AppDatabase.getInstance(context)
        dao = appDatabase.pdfFilesDao()
        repository = PdfFilesRepository.Base(dataSource, dao)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun is_all_data_is_true_state() = runBlocking {
        val list = repository.fetchFavorites().toList()[0]
        val list1 = ArrayList<PdfFileDb>()
        list1.addAll(list)

        for (i in 0 until list1.size) {
            assertEquals(true, list1[i].favorite)
        }
    }


    @After
    fun cancelAll() {
        appDatabase.close()
    }
}
