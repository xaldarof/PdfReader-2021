package pdf.reader.simplepdfreader.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.fake_test_data.PdfFilesDaoForTest
import pdf.reader.simplepdfreader.fake_test_data.PdfFilesRepositoryForTest

fun provideTestDao(context: Context):PdfFilesDaoForTest{
    return AppDatabase.getInstance(context).testPdfFilesDao()
}

fun provideTestRepository(context: Context):PdfFilesRepositoryForTest {
    return PdfFilesRepositoryForTest.Base(provideTestDao(context))
}
val testAppModule = module {
    single { provideTestRepository(androidApplication().applicationContext) }
}