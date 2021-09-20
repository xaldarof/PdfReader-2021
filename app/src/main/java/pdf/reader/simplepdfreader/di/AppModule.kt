package pdf.reader.simplepdfreader.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.core.CacheRepository
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import pdf.reader.simplepdfreader.data.core.ReadingFileRepository
import pdf.reader.simplepdfreader.data.cache.*
import pdf.reader.simplepdfreader.data.cloud.BookCloudDataSource
import pdf.reader.simplepdfreader.data.cloud.BookRepository
import pdf.reader.simplepdfreader.data.cloud.api.ApiService
import pdf.reader.simplepdfreader.data.data_source.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.data.room.BookDao
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import pdf.reader.simplepdfreader.presentation.viewmodels.CoreFragmentViewModel
import pdf.reader.simplepdfreader.presentation.viewmodels.HistoryFragmentViewModel
import pdf.reader.simplepdfreader.presentation.viewmodels.SearchActivityViewModel
import pdf.reader.simplepdfreader.tools.ByteToMbConverter
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<ByteToMbConverter> { ByteToMbConverter.Base() }
    single<PdfFilesDao> { AppDatabase.getInstance(androidContext()).pdfFilesDao() }
    single<BookDao> { AppDatabase.getInstance(androidContext()).bookDao() }
    single<PdfFilesDataSourceMobile> { PdfFilesDataSourceMobile.Base(get()) }
    single<ReadingFileRepository> { ReadingFileRepository.Base(get()) }
    single<PdfFilesRepository> { PdfFilesRepository.Base(get(), get()) }
}

val cacheModule = module {
    factory<SharedPreferences> { androidContext().getSharedPreferences("cache", MODE_PRIVATE) }
    factory { DarkThemeCache(DarkThemeCacheImpl(get())) }
    factory { HorizontalScrollingCache(HorizontalScrollingCacheImpl(get())) }
    factory { AutoSpacingStateCache(AutoSpacingStateCacheImpl(get())) }
    factory<CacheRepository> { CacheRepository.Base(get(), get(), get()) }
}

private const val URL = "https://www.googleapis.com/books/v1/"
fun provideApiService(retrofit: retrofit2.Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

val cloudModule = module {
    single {
        retrofit2.Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory<BookCloudDataSource> { BookCloudDataSource.Base(provideApiService(get())) }
    factory<BookCacheDataSource> { BookCacheDataSource.Base(get()) }
    factory<BookRepository> { BookRepository.Base(get(), get()) }

    single { provideApiService(get()) }
}

val viewModels: Module = module {
    viewModel { CoreFragmentViewModel(get()) }
    viewModel { SearchActivityViewModel(get()) }
    viewModel { HistoryFragmentViewModel(get()) }
}
