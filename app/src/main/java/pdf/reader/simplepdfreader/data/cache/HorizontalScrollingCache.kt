package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class HorizontalScrollingCache(private val horizontalScrollingCacheImpl: HorizontalScrollingCacheImpl) : Write<Boolean>,Read<Boolean>{
    override fun read(): Boolean {
        return horizontalScrollingCacheImpl.read()
    }

    override fun save(data: Boolean) {
        horizontalScrollingCacheImpl.save(data)
    }

}