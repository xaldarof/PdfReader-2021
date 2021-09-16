package pdf.reader.simplepdfreader.data.core

import pdf.reader.simplepdfreader.data.cache.*

interface CacheRepository {

    fun readDarkThemeCache(): Boolean
    fun readAutoSpacingCache(): Boolean
    fun readHorScrollCache(): Boolean

    fun writeDarkThemeCache(state: Boolean)
    fun writAutoSpacingCache(state: Boolean)
    fun writeHorScrollCache(state: Boolean)


    class Base(
        private val darkThemeCache: DarkThemeCache,
        private val autoSpacingStateCache: AutoSpacingStateCache,
        private val horizontalScrollingCache: HorizontalScrollingCache
    ) : CacheRepository {

        override fun readDarkThemeCache(): Boolean {
            return darkThemeCache.read()
        }

        override fun readAutoSpacingCache(): Boolean {
            return autoSpacingStateCache.read()
        }

        override fun readHorScrollCache(): Boolean {
            return horizontalScrollingCache.read()
        }


        override fun writeDarkThemeCache(state: Boolean) {
            darkThemeCache.save(state)
        }

        override fun writAutoSpacingCache(state: Boolean) {
            autoSpacingStateCache.save(state)
        }

        override fun writeHorScrollCache(state: Boolean) {
           horizontalScrollingCache.save(state)
        }


    }
}