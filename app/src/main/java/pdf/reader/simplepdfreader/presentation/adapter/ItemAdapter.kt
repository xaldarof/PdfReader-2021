package pdf.reader.simplepdfreader.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.ItemBinding
import kotlin.collections.ArrayList
import com.google.android.material.snackbar.Snackbar
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.presentation.dialogs.EditDialog
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import java.io.*

class ItemAdapter (
    private val onClickListener: OnClickListener,
    private val myPdfRenderer: MyPdfRenderer,
    private val recyclerView: RecyclerView,
    private val context: Context
) : RecyclerView.Adapter<ItemAdapter.VH>() {

    private var list = ArrayList<PdfFileDb>()

    fun setData(newList: List<PdfFileDb>) {
        val itemDiffUtilCallBack = ItemDiffUtilCallBack(list, newList)
        val diffUtil = DiffUtil.calculateDiff(itemDiffUtilCallBack, true)
        list.clear()
        list.addAll(newList)
        diffUtil.dispatchUpdatesTo(this)
    }

    fun updateListForSearch(filteredList: List<PdfFileDb>) {
        list.clear()
        list.addAll(filteredList)
        val itemDiffUtilCallBack = ItemDiffUtilCallBack(list, filteredList)
        val diffUtil = DiffUtil.calculateDiff(itemDiffUtilCallBack, true)
        diffUtil.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    private val colors = arrayOf(context.resources.getColor(R.color.def_color),
        context.resources.getColor(R.color.colorRed))

    inner class VH(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        @KoinApiExtension
        fun onBind(pdfFile: PdfFileDb) {
            item.fileName.text = pdfFile.name
            item.sizeTv.text = pdfFile.size
            item.imageView.setImageBitmap(myPdfRenderer.getBitmap(File(pdfFile.dirName), item.imageView))
            item.progress.max = pdfFile.pageCount
            item.progress.progress = pdfFile.lastPage

            item.layout.setOnClickListener { onClickListener.onClick(pdfFile) }
            item.editBtn.setOnClickListener { EditDialog.Base(context,pdfFile).showDialog() }

            if (pdfFile.favorite) {
                item.favoriteState.setColorFilter(colors[1])
            } else {
                item.favoriteState.setColorFilter(colors[0])
            }

            if (pdfFile.interesting) {
                item.interestingState.setColorFilter(colors[1])
            } else {
                item.interestingState.setColorFilter(colors[0])
            }

            if (pdfFile.willRead) {
                item.willReadState.setColorFilter(colors[1])
            } else {
                item.willReadState.setColorFilter(colors[0])
            }

            if (pdfFile.finished) {
                item.doneState.setColorFilter(colors[1])
            } else {
                item.doneState.setColorFilter(colors[0])
            }

            //CLICKS FAVORITES
            item.favoriteState.setOnClickListener {
                if (pdfFile.favorite) {
                    Snackbar.make(recyclerView, "?????????????? ???? '??????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "?????????????????? ?? '??????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddToFavorites(pdfFile)
            }

            //CLICKS INTERESTING
            item.interestingState.setOnClickListener {
                if (pdfFile.interesting) {
                    Snackbar.make(recyclerView, "?????????????? ???? '????????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "?????????????????? ?? '????????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddTooInteresting(pdfFile)
            }

            //CLICKS WILL READ
            item.willReadState.setOnClickListener {
                if (pdfFile.willRead) {
                    Snackbar.make(recyclerView, "?????????????? ???? '???????? ????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "?????????????????? ?? '???????? ????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddToWillRead(pdfFile)
            }

            //CLICKS FINISHED
            item.doneState.setOnClickListener {
                if (pdfFile.finished) {
                    Snackbar.make(recyclerView, "?????????????? ???? '??????????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "?????????????????? ?? '??????????????????????'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddToFinished(pdfFile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClickListener {
        fun onClick(pdfFileDb: PdfFileDb)

        fun onClickAddToFavorites(pdfFileDb: PdfFileDb)

        fun onClickAddTooInteresting(pdfFileDb: PdfFileDb)

        fun onClickAddToWillRead(pdfFileDb: PdfFileDb)

        fun onClickAddToFinished(pdfFileDb: PdfFileDb)

    }
}
