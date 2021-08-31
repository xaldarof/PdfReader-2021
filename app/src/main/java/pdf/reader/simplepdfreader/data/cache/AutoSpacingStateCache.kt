package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class AutoSpacingStateCache(private val autoSpacingStateCacheImpl: AutoSpacingStateCacheImpl):Read<Boolean>,Write<Boolean> {
    override fun read(): Boolean {
       return autoSpacingStateCacheImpl.read()
    }

    override fun save(data: Boolean) {
       autoSpacingStateCacheImpl.save(data)
    }

}