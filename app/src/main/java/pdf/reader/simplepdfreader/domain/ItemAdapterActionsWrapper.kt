package pdf.reader.simplepdfreader.domain

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

class ItemAdapterActionsWrapper(private val viewModelProvider: CoreFragmentViewModel, private val context: Context) : ItemAdapter.OnClickListener {

    private var position = 0

    fun init(myPdfRenderer: MyPdfRenderer,recyclerView: RecyclerView,context: Context,lifecycleOwner: LifecycleOwner){
       val itemAdapter = ItemAdapter(this,myPdfRenderer,recyclerView,context)
        viewModelProvider.fetchPdfFiles().observe(lifecycleOwner,{
            itemAdapter.update(it,getItemPosition())
        })
        recyclerView.adapter = itemAdapter
    }

    override fun onClick(pdfFileDb: PdfFileDb) {
        NextActivity.Base(context)
            .startActivity(PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
    }

    override fun onClickAddToFavorites(pdfFileDb: PdfFileDb, position: Int) {
        viewModelProvider.updateFavoriteState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddTooInteresting(pdfFileDb: PdfFileDb, position: Int) {
        viewModelProvider.updateInterestingState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddToWillRead(pdfFileDb: PdfFileDb, position: Int) {
        viewModelProvider.updateWillReadState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddToFinished(pdfFileDb: PdfFileDb, position: Int) {
        viewModelProvider.updateFinishedState(pdfFileDb)
        this.position = position
    }

    fun getItemPosition() : Int{
        return position
    }
}