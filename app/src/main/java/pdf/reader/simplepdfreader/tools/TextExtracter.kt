package pdf.reader.simplepdfreader.tools

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import java.lang.Exception

interface TextExtracter {

    suspend fun extractText(path: String, page: Int):String

    class Base : TextExtracter {

        private var parsedText = ""

        override suspend fun extractText(path: String, page: Int): String = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                try {
                    val reader = PdfReader(path)
                    parsedText = PdfTextExtractor.getTextFromPage(reader, page)

                    parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, page)
                        .trim { it <= ' ' } + "\n"

                    reader.close()

                } catch (e: Exception) {
                    println(e)
                }
            }
            return@withContext parsedText
        }
    }
}