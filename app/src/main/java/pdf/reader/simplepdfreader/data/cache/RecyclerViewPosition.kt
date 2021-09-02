package pdf.reader.simplepdfreader.data.cache

import pdf.reader.simplepdfreader.core.Read
import pdf.reader.simplepdfreader.core.Write

class RecyclerViewPosition(private val recyclerViewPosition: RecyclerViewPositionImpl):Write<Int>,Read<Int>{
    override fun save(data: Int) {
        recyclerViewPosition.save(data)
    }

    override fun read(): Int {
        return recyclerViewPosition.read()
    }


}