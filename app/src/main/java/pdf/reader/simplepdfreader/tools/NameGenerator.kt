package pdf.reader.simplepdfreader.tools

import java.util.*

interface NameGenerator {

    fun getAutoName():String

    class Base : NameGenerator {
        override fun getAutoName() = Date().time.toString()
    }
}