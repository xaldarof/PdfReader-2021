package pdf.reader.simplepdfreader.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.ItemBinding
import android.graphics.Bitmap

import kotlin.collections.ArrayList
import android.os.ParcelFileDescriptor
import android.text.Layout
import com.google.android.material.snackbar.Snackbar
import com.shockwave.pdfium.PdfDocument

import com.shockwave.pdfium.PdfiumCore
import kotlinx.coroutines.*
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import java.io.*


class ItemAdapter(
    private val onClickListener: OnClickListener,
    private val myPdfRenderer: MyPdfRenderer,
    private val recyclerView: RecyclerView,
    private val context: Context
) : RecyclerView.Adapter<ItemAdapter.VH>() {

    private val list = ArrayList<PdfFileDb>()

    fun update(list: List<PdfFileDb>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(pdfFile: PdfFileDb) {
            item.fileName.text = pdfFile.name
            item.sizeTv.text = pdfFile.size
            item.imageView.setImageBitmap(
                myPdfRenderer.getBitmap(
                    File(pdfFile.dirName),
                    item.imageView
                )
            )

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
                    item.interestingState.setColorFilter(Color.BLACK)
                    Snackbar.make(recyclerView, "Удалено из 'Интересные'", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    item.interestingState.setColorFilter(Color.RED)
                    Snackbar.make(recyclerView, "Добавлено в 'Интересные'", Snackbar.LENGTH_SHORT)
                        .show()
                }
                onClickListener.onClickAddTooInteresting(pdfFile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(this.list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onClick(pdfFileDb: PdfFileDb)

        fun onClickAddToFavorites(pdfFileDb: PdfFileDb)

        fun onClickAddTooInteresting(pdfFileDb: PdfFileDb)
    }
}