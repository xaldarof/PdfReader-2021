package pdf.reader.simplepdfreader.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pdf.reader.simplepdfreader.data.PdfFilesRepository
import pdf.reader.simplepdfreader.data.ReadingFileRepository
import pdf.reader.simplepdfreader.data.data_source.PdfFilesDataSourceMobile
import pdf.reader.simplepdfreader.data.room.AppDatabase
import pdf.reader.simplepdfreader.data.room.PdfFilesDao


val appModule = module {
    single<PdfFilesDao> { AppDatabase.getInstance(androidContext()).pdfFilesDao() }
    single<PdfFilesDataSourceMobile> { PdfFilesDataSourceMobile.Base() }
    single<ReadingFileRepository> { ReadingFileRepository.Base(get()) }
    single<PdfFilesRepository> { PdfFilesRepository.Base(get(),get()) }

}