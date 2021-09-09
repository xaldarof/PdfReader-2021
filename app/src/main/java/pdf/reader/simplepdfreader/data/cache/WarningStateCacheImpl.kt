package pdf.reader.simplepdfreader.data.cache

import android.content.SharedPreferences
import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write


class WarningStateCacheImpl(private val sharedPreferences: SharedPreferences)  : Write<Boolean>, Read<Boolean> {
    override fun save(data: Boolean) {
        sharedPreferences.edit().putBoolean(WARNING,data).apply()
    }

    override fun read(): Boolean = sharedPreferences.getBoolean(WARNING,false)


    private companion object {
        const val WARNING = "warning_cache"
    }
}