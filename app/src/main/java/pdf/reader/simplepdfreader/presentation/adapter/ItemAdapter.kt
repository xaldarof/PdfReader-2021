package pdf.reader.simplepdfreader.presentation.adapter

import android.content.Context
import android.util.Log
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
import pdf.reader.simplepdfreader.presentation.EditDialog
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

    inner class VH(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        @KoinApiExtension
        fun onBind(pdfFile: PdfFileDb) {
            item.fileName.text = pdfFile.name
            item.sizeTv.text = pdfFile.size
            item.imageView.setImageBitmap(myPdfRenderer.getBitmap(File(pdfFile.dirName), item.imageView))
            item.progress.max = pdfFile.pageCount
            item.progress.progress = pdfFile.lastPage

            item.editBtn.setOnClickListener {
                EditDialog.Base(context,pdfFile.dirName,pdfFile.name).showDialog()
            }

            item.layout.setOnClickListener { onClickListener.onClick(pdfFile) }

            if (pdfFile.favorite) {
                item.favoriteState.setColorFilter(context.resources.getColor(R.color.colorRed))
            } else {
                item.favoriteState.setColorFilter(context.resources.getColor(R.color.def_color))
            }

            if (pdfFile.interesting) {
                item.interestingState.setColorFilter(context.resources.getColor(R.color.colorRed))
            } else {
                item.interestingState.setColorFilter(context.resources.getColor(R.color.def_color))
            }

            if (pdfFile.willRead) {
                item.willReadState.setColorFilter(context.resources.getColor(R.color.colorRed))
            } else {
                item.willReadState.setColorFilter(context.resources.getColor(R.color.def_color))
            }

            if (pdfFile.finished) {
                item.doneState.setColorFilter(context.resources.getColor(R.color.colorRed))
            } else {
                item.doneState.setColorFilter(context.resources.getColor(R.color.def_color))
            }

            //CLICKS FAVORITES
            item.favoriteState.setOnClickListener {
                if (pdfFile.favorite) {
                    Snackbar.make(recyclerView, "Удалено из 'Избранные'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "Добавлено в 'Избранные'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddToFavorites(pdfFile)
            }

            //CLICKS INTERESTING
            item.interestingState.setOnClickListener {
                if (pdfFile.interesting) {
                    Snackbar.make(recyclerView, "Удалено из 'Интересные'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "Добавлено в 'Интересные'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddTooInteresting(pdfFile)
            }

            //CLICKS WILL READ
            item.willReadState.setOnClickListener {
                if (pdfFile.willRead) {
                    Snackbar.make(recyclerView, "Удалено из 'Буду читать'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "Добавлено в 'Буду читать'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddToWillRead(pdfFile)
            }

            //CLICKS FINISHED
            item.doneState.setOnClickListener {
                if (pdfFile.finished) {
                    Snackbar.make(recyclerView, "Удалено из 'Прочитанные'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(recyclerView, "Добавлено в 'Прочитанные'", Snackbar.LENGTH_SHORT)
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
