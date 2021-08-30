package pdf.reader.simplepdfreader.tools

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter

interface OnClick {

}
class ItemAdapterWrapper(private val context: Context,private val recyclerView: RecyclerView) : ItemAdapter.OnClickListener{

    fun notifyData(){
        val myPdfRenderer = MyPdfRenderer(context)
        val itemAdapter = ItemAdapter(this,myPdfRenderer,recyclerView,context)
        itemAdapter.notifyDataSetChanged()
    }

    override fun onClick(pdfFileDb: PdfFileDb) {

    }
    override fun onClickAddToFavorites(pdfFileDb: PdfFileDb, position: Int) {

    }

    override fun onClickAddTooInteresting(pdfFileDb: PdfFileDb, position: Int) {
    }

    override fun onClickAddToWillRead(pdfFileDb: PdfFileDb, position: Int) {

    }

    override fun onClickAddToFinished(pdfFileDb: PdfFileDb, position: Int) {
    }
}