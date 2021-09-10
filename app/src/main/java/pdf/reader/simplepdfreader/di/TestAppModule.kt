package pdf.reader.simplepdfreader.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesDao
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesRepository

fun provideTestDao(context: Context):FakePdfFilesDao{
    return AppDatabase.getInstance(context).testPdfFilesDao()
}

fun provideTestRepository(context: Context):FakePdfFilesRepository {
    return FakePdfFilesRepository.Base(provideTestDao(context))
}
val testAppModule = module {
    single { provideTestRepository(androidApplication().applicationContext) }
}