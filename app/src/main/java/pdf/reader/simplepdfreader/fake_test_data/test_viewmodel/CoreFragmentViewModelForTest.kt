package pdf.reader.simplepdfreader.fake_test_data.test_viewmodel

import pdf.reader.simplepdfreader.fake_test_data.PdfFilesRepositoryForTest
import androidx.lifecycle.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CoreFragmentViewModelForTest : ViewModel(), KoinComponent {

    private val pdfFilesRepository: PdfFilesRepositoryForTest by inject()

    suspend fun fetchPdfFiles() = pdfFilesRepository.fetchTestPdfFiles()

    suspend fun fetchFavorites() = pdfFilesRepository.fetchTestFavorites()

    suspend fun fetchIntersting() = pdfFilesRepository.fetchTestFavorites()

    suspend fun fetchWillRead() = pdfFilesRepository.fetchTestFavorites()

//    suspend fun fetchFavorites() = pdfFilesRepository.fetchTestFavorites()
//
//    suspend fun fetchFavorites() = pdfFilesRepository.fetchTestFavorites()
}