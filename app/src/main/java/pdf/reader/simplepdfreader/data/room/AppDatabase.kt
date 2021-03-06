package pdf.reader.simplepdfreader.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesDao

@Database(entities = [PdfFileDb::class, BookDb::class], version = 16, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pdfFilesDao(): PdfFilesDao
    abstract fun bookDao(): BookDao

    abstract fun testPdfFilesDao(): FakePdfFilesDao

    companion object {
        private const val DATABASE_NAME = "files"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}