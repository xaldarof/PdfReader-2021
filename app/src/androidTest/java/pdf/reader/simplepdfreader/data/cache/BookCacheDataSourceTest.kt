package pdf.reader.simplepdfreader.data.cache

import android.util.Log
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.room.BookDb

@KoinApiExtension
class BookCacheDataSourceTest : KoinComponent {

    private val bookCacheDataSource: BookCacheDataSource by inject()

    @Test
    fun check_is_database_returns_data():Unit = runBlocking {
        CoroutineScope(Dispatchers.Main).launch {
            bookCacheDataSource.fetchBooks().asLiveData().observeForever {
                assertTrue(it.isNotEmpty())
            }
        }
    }

    @Test
    fun check_is_data_inserted(): Unit = runBlocking{
        val testBookDbInsert = BookDb("TEST_TITLE_aDELETE12","qTEST_AUTHOR","TEST_IqSBN","TEST_qTIME")
        bookCacheDataSource.insertBook(testBookDbInsert)
        CoroutineScope(Dispatchers.Main).launch {
            bookCacheDataSource.fetchBooks().asLiveData().observeForever {
                assertTrue(it.contains(testBookDbInsert))
                Log.d("pos","$it")
            }
        }
    }

    @Test
    fun check_is_data_deletes(): Unit = runBlocking {
        val testBookDbDelete = BookDb("TEST_TITLdE_DELETE12","TEST_wwdAUTHOR","TEwST_ISBN","TEST_TIMEw")
        bookCacheDataSource.deleteBook(testBookDbDelete)
        CoroutineScope(Dispatchers.Main).launch {
            bookCacheDataSource.fetchBooks().asLiveData().observeForever {
                assertTrue(!it.contains(testBookDbDelete))
            }
        }
    }
}