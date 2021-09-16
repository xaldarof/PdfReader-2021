package pdf.reader.simplepdfreader.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.android.*
import org.koin.core.context.startKoin
import pdf.reader.simplepdfreader.di.appModule

class SimplePdfReader : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SimplePdfReader)
            modules(appModule)
        }
    }
}