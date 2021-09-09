package pdf.reader.simplepdfreader.domain

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.fake_test_data.test_viewmodel.CoreFragmentViewModelForTest

@KoinApiExtension
class CoreFragmentViewModelTest {

    private val viewModel = CoreFragmentViewModelForTest()

    @Test
    fun check_is_viewModel_return_data_from_repository(): Unit = runBlocking {
        val list = viewModel.fetchPdfFiles()
        val actual = list.isNotEmpty()
        assertEquals(true,actual)
    }

    @Test
    fun check_is_viewModel_return_favorite_state_data_from_repository(): Unit = runBlocking {
        val list = viewModel.fetchFavorites()
        val actual = list.isNotEmpty()
        assertEquals(true,actual)
    }

    @Test
    fun check_is_viewModel_returns_interesting_state_data_from_repository(): Unit = runBlocking {
        val list = viewModel.fetchIntersting()
        val actual = list.isNotEmpty()
        assertEquals(true,actual)
    }

    @Test
    fun check_is_viewModel_return_willRead_state_data_from_repository(): Unit = runBlocking {
        val list = viewModel.fetchWillRead()
        val actual = list.isNotEmpty()
        assertEquals(true,actual)
    }
}