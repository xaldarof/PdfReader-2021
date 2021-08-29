package pdf.reader.simplepdfreader.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.ItemBinding
import kotlin.collections.ArrayList
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import java.io.*


class InterestingAdapter(
    private val onClickListener: OnClickListener,
    private val myPdfRenderer: MyPdfRenderer,
    private val recyclerView: RecyclerView,
    private val context: Context
) : RecyclerView.Adapter<InterestingAdapter.VH>() {

    private val list = ArrayList<PdfFileDb>()

    fun update(list: List<PdfFileDb>, position: Int) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemChanged(position)
    }

    inner class VH(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(pdfFile: PdfFileDb, position: Int) {
            item.fileName.text = pdfFile.name
            item.sizeTv.text = pdfFile.size
            item.imageView.setImageBitmap(
                myPdfRenderer.getBitmap(
                    File(pdfFile.dirName),
                    item.imageView
                )
            )
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
                onClickListener.onClickAddToFavorites(pdfFile, position)
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
                onClickListener.onClickAddTooInteresting(pdfFile, position)
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
                onClickListener.onClickAddToWillRead(pdfFile, position)
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
                onClickListener.onClickAddToFinished(pdfFile, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(this.list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onClick(pdfFileDb: PdfFileDb)

        fun onClickAddToFavorites(pdfFileDb: PdfFileDb, position: Int)

        fun onClickAddTooInteresting(pdfFileDb: PdfFileDb, position: Int)

        fun onClickAddToWillRead(pdfFileDb: PdfFileDb, position: Int)

        fun onClickAddToFinished(pdfFileDb: PdfFileDb, position: Int)
    }
}