package pdf.reader.simplepdfreader.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.ReadingFileRepository
import pdf.reader.simplepdfreader.data.cache.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.data.room.PdfFilesDao
import pdf.reader.simplepdfreader.data.room.PdfFilesFromFolderRepository

fun providePdfFilesDao(context:Context):PdfFilesDao{
    val db = AppDatabase.getInstance(context)
    return db.pdfFilesDao()
}

fun provideRepository(context: Context):PdfFilesRepository {
    val dataSource = PdfFilesDataSourceMobile.Base()
    return PdfFilesRepository.Base(dataSource, providePdfFilesDao(context))
}

fun provideSinglePdfFileRepository(context: Context):PdfFilesFromFolderRepository{
    return PdfFilesFromFolderRepository.Base(providePdfFilesDao(context))
}
fun provideReadingPdfFileRepository(context: Context):ReadingFileRepository{
    return ReadingFileRepository.Base(providePdfFilesDao(context))
}

val appModule = module {
    single { providePdfFilesDao(androidApplication().applicationContext) }
    single { provideRepository(androidApplication().applicationContext) }
    single { provideSinglePdfFileRepository(androidApplication().applicationContext) }
    single { provideReadingPdfFileRepository(androidApplication().applicationContext) }
}