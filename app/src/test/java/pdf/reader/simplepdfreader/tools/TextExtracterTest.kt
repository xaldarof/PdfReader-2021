package pdf.reader.simplepdfreader.tools

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TextExtracterTest {

    @Test
    fun is_extracts_text_from_pdf_file() {
        val textExtracter = TextExtracter.Base()
        var actual = ""
        CoroutineScope(Dispatchers.IO).launch {
            actual = textExtracter.extractText("", 0)

        }
        assertThat(actual).isEmpty()
    }
}