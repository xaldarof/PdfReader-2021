package pdf.reader.simplepdfreader.tools

import java.io.File

interface DirectoryDeleter {

    fun delete(dir:String)

    class Base : DirectoryDeleter {
        override fun delete(dir: String) {
            File(dir).delete()
        }
    }
}