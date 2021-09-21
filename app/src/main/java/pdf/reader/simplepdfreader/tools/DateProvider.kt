package pdf.reader.simplepdfreader.tools

import java.text.SimpleDateFormat
import java.util.*

interface DateProvider {

    fun getDate():String
    fun getFormattedDate():String

    class Base : DateProvider {
        override fun getDate() = Date().time.toString()
        override fun getFormattedDate(): String = SimpleDateFormat("dd/MM/yyyy").format(Date())

    }
}