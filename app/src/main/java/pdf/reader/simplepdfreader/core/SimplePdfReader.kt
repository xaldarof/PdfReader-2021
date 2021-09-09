package pdf.reader.simplepdfreader.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pdf.reader.simplepdfreader.di.appModule
import pdf.reader.simplepdfreader.di.testAppModule

class SimplePdfReader : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SimplePdfReader)
            modules(appModule, testAppModule)
        }
    }
}