package pdf.reader.simplepdfreader.data.core

import org.junit.Assert.*
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CacheRepositoryTest : KoinComponent {

    private val cacheRepository: CacheRepository by inject()

    @Test
    fun check_is_dark_theme_state_changed_to_true() {
        cacheRepository.writeDarkThemeCache(true)
        assertEquals(true, cacheRepository.readDarkThemeCache())
    }

    @Test
    fun check_is_autoSpacing_state_changed_to_true() {
        cacheRepository.writAutoSpacingCache(true)
        assertEquals(true, cacheRepository.readAutoSpacingCache())
    }

    @Test
    fun check_is_horizontalScrolling_state_to_true() {
        cacheRepository.writeHorScrollCache(true)
        assertEquals(true, cacheRepository.readHorScrollCache())
    }




    @Test
    fun check_is_dark_theme_state_changed_to_false() {
        cacheRepository.writeDarkThemeCache(false)
        assertEquals(false, cacheRepository.readDarkThemeCache())
    }

    @Test
    fun check_is_autoSpacing_state_changed_to_false() {
        cacheRepository.writAutoSpacingCache(false)
        assertEquals(false, cacheRepository.readAutoSpacingCache())
    }

    @Test
    fun check_is_horizontalScrolling_state_to_false() {
        cacheRepository.writeHorScrollCache(false)
        assertEquals(false, cacheRepository.readHorScrollCache())
    }


}