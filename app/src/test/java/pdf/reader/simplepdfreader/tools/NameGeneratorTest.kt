package pdf.reader.simplepdfreader.tools

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NameGeneratorTest {

    private lateinit var nameGenerator: NameGenerator

    @Before
    fun setUp(){
        nameGenerator = NameGenerator.Base()
    }

    @Test
    fun is_generate_name(){
        val actualName = nameGenerator.getAutoName()
        assertThat(actualName).isNotEmpty()
    }

}