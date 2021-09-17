package pdf.reader.simplepdfreader.tools

import org.junit.Assert.*
import org.junit.Test

class ByteToMbConverterTest{

    @Test
    fun check_is_converts_true_value(){
        val converter = ByteToMbConverter.Base()
        val actual = converter.convert(5000000)
        assertEquals("4,77 МБ",actual)
    }
}