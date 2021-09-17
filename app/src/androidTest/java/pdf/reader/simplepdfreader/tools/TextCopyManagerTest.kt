package pdf.reader.simplepdfreader.tools

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test

class TextCopyManagerTest{

    private companion object{
        const val TEST_TEXT = "Unit Testing 17.09.2021"
    }

    @Test
    fun check_is_text_copied_to_clipboard() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val textCopyManager = TextCopyManager.Base(context)
        textCopyManager.copyToClipboard(TEST_TEXT)
    }
}