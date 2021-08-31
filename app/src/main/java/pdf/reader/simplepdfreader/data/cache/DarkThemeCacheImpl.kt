package pdf.reader.simplepdfreader.data.cache

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class DarkThemeCacheImpl(private val sharedPreferences: SharedPreferences) : Read<Boolean>, Write<Boolean> {


    override fun read() = sharedPreferences.getBoolean(DARK_THEME_MODE, false)

    override fun save(data: Boolean) {

        sharedPreferences.edit().putBoolean(DARK_THEME_MODE, data).apply()
    }

    companion object {
        const val DARK_THEME_MODE = "darkTheme"
    }

}