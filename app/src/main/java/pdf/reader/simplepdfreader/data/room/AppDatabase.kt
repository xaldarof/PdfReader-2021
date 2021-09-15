package pdf.reader.simplepdfreader.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pdf.reader.simplepdfreader.fake_test_data.FakePdfFilesDao

@Database(entities = [PdfFileDb::class],version = 12,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pdfFilesDao(): PdfFilesDao
    abstract fun testPdfFilesDao():FakePdfFilesDao

    companion object {
        private const val DATABASE_NAME = "files"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}