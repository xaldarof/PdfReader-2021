package pdf.reader.simplepdfreader.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import pdf.reader.simplepdfreader.data.room.PdfFileDb

class ItemDiffUtilCallBack(private val oldList:List<PdfFileDb>,private val newList: List<PdfFileDb>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPdfFileDb = oldList[oldItemPosition]
        val newPdfFileDb = newList[newItemPosition]
        return oldPdfFileDb.dirName == newPdfFileDb.dirName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPdfFileDb = oldList[oldItemPosition]
        val newPdfFileDb = newList[newItemPosition]
        return oldPdfFileDb.name == newPdfFileDb.name  &&
                oldPdfFileDb.willRead == newPdfFileDb.willRead &&
                oldPdfFileDb.interesting == newPdfFileDb.interesting &&
                oldPdfFileDb.favorite == newPdfFileDb.favorite  &&
                oldPdfFileDb.reading == newPdfFileDb.reading  &&
                oldPdfFileDb.finished == newPdfFileDb.finished  &&
                oldPdfFileDb.lastPage == newPdfFileDb.lastPage  &&
                oldPdfFileDb.isEverOpened == newPdfFileDb.isEverOpened &&
                oldPdfFileDb.pageCount == newPdfFileDb.pageCount

    }

}