package pdf.reader.simplepdfreader.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.data.room.PdfFilesDao

fun providePdfFilesDao(context:Context):PdfFilesDao{
    val db = AppDatabase.getInstance(context)
    return db.pdfFilesDao()
}

fun provideRepository(context: Context):PdfFilesRepository {
    val dataSource = PdfFilesDataSourceMobile.Base(context)
    return PdfFilesRepository.Base(dataSource, providePdfFilesDao(context))
}

val appModule = module {
    single { providePdfFilesDao(androidApplication().applicationContext) }
    single { provideRepository(androidApplication().applicationContext) }
}