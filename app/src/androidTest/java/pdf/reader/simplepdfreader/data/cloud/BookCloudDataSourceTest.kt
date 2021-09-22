package pdf.reader.simplepdfreader.data.cloud

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class BookCloudDataSourceTest : KoinComponent {

    private val bookCloudDataSource:BookCloudDataSource by inject()
    private companion object {
        const val TEST_ISBN = "9780747532743"
    }

    @Test
    fun check_is_data_comes_in_true_ISBN(): Unit = runBlocking {
        val actual = bookCloudDataSource.fetchBookInfo(TEST_ISBN)
        assertNotNull(actual)
    }
}