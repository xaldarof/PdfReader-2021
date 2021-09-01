package pdf.reader.simplepdfreader.data.cache

import android.content.SharedPreferences
import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class HorizontalScrollingCacheImpl(private val sharedPreferences: SharedPreferences) : Write<Boolean>,Read<Boolean> {
    override fun read(): Boolean {
        return sharedPreferences.getBoolean(HORIZONTAL_SCROLL,false)
    }

    override fun save(data: Boolean) {
        sharedPreferences.edit().putBoolean(HORIZONTAL_SCROLL,data).apply()
    }
   private companion object {
        const val HORIZONTAL_SCROLL = "hor_cache"
    }
}