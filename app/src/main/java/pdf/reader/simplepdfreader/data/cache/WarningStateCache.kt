package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class WarningStateCache(private val warningStateCacheImpl: WarningStateCacheImpl) : Write<Boolean>,Read<Boolean> {
    override fun read(): Boolean {
        return warningStateCacheImpl.read()
    }

    override fun save(data: Boolean) {
        warningStateCacheImpl.save(data)
    }
}