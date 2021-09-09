package pdf.reader.simplepdfreader.tools

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TextExtracterTest {

    //Need test in true pdfFile and dir

    @Test
    fun is_extracts_text_from_pdf_fileDirectory() = runBlocking {
        val textExtracter = TextExtracter.Base()
        val testDir = "/storage/emulated/0/Download/Practical_Vim_Edit.pdf"
        val actual = textExtracter.extractText(testDir, 10)

        assertEquals(true,actual.isNotEmpty())

    }
}