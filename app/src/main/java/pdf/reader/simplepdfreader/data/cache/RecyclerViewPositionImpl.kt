package pdf.reader.simplepdfreader.data.cache

import android.content.SharedPreferences
import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class RecyclerViewPositionImpl(private val sharedPreferences: SharedPreferences) : Write<Int>, Read<Int> {
    override fun read(): Int {
        return sharedPreferences.getInt(LAST_POSITION,0)
    }

    override fun save(data: Int) {
        sharedPreferences.edit().putInt(LAST_POSITION,data).apply()
    }
    private companion object {
        const val LAST_POSITION = "position"
    }
}