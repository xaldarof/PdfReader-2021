package pdf.reader.simplepdfreader.tools

import android.util.Log
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.lang.Exception

interface TextExtracter {

    fun extractText(path: String, page: Int):String

    class Base : TextExtracter {

        private lateinit var parsedText: String

        override fun extractText(path: String, page: Int): String {
            try {
                val reader = PdfReader(path)
                parsedText = PdfTextExtractor.getTextFromPage(reader, page)

                Log.d("pos", parsedText)
                parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader,page)
                            .trim { it <= ' ' } + "\n"

                reader.close()

            } catch (e: Exception) {
                println(e)
            }
            return parsedText
        }
    }
}