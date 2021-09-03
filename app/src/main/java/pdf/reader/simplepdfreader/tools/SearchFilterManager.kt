package pdf.reader.simplepdfreader.tools

import android.util.Log
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter

interface SearchFilterManager {

    fun filter(filterString: String)

    class Base(private val list: List<PdfFileDb>, private val itemAdapter: ItemAdapter) : SearchFilterManager {

        override fun filter(filterString: String) {
            val tempList = ArrayList<PdfFileDb>()
            for (i in list) {
                if (i.name.contains(filterString)) {
                    tempList.add(i)
                }
            }
            itemAdapter.updateListForSearch(tempList)
        }
    }
}
