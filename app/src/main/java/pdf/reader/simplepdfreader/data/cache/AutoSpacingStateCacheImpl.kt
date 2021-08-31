package pdf.reader.simplepdfreader.data.cache

import android.content.SharedPreferences
import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class AutoSpacingStateCacheImpl(private val sharedPreferences: SharedPreferences) : Read<Boolean>,Write<Boolean> {
    override fun read() = sharedPreferences.getBoolean(AUTO_SPACING,false)

    override fun save(data: Boolean) {
        sharedPreferences.edit().putBoolean(AUTO_SPACING,data).apply()
    }

    companion object {
        const val AUTO_SPACING = "auto_spacing"
    }
}