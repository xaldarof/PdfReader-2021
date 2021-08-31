package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class DarkThemeCache(private val darkThemeCacheImpl: DarkThemeCacheImpl):Read<Boolean>,Write<Boolean> {
    override fun read(): Boolean {
        return darkThemeCacheImpl.read()
    }

    override fun save(data: Boolean) {
        darkThemeCacheImpl.save(data)
    }

}