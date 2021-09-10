package pdf.reader.simplepdfreader.fake_test_data

import android.content.Context
import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile

class FakePdfFilesDataSourceTest {

    private lateinit var fakePdfFilesDataSource: FakePdfFilesDataSource
    private lateinit var pdfFilesDataSourceMobile: PdfFilesDataSourceMobile

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pdfFilesDataSourceMobile = PdfFilesDataSourceMobile.Base()
        fakePdfFilesDataSource = FakePdfFilesDataSource(pdfFilesDataSourceMobile)
    }

    //if Permission granted

    @Test
    fun check_is_data_source_return_data_from_device_storage() = runBlocking {
        val actual = fakePdfFilesDataSource.findFilesAndFetch(Environment.getExternalStorageDirectory())
        assertNotNull(actual)
    }
}