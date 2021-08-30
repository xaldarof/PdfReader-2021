package pdf.reader.simplepdfreader.tools

interface ReadingCountManager {


    class Base():ReadingCountManager
    fun updateData(dirName: String, lastPage: Int) {

    }
}