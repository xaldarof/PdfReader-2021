package pdf.reader.simplepdfreader.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.room.BookDb
import pdf.reader.simplepdfreader.databinding.BooksCacheItemBinding
import pdf.reader.simplepdfreader.presentation.dialogs.BookPopMenu
import pdf.reader.simplepdfreader.presentation.fragments.net_fragment.SearchBookFragment
import pdf.reader.simplepdfreader.tools.DateProvider
import java.lang.reflect.Field

class BookCacheItemAdapter(private val bookPopMenu: BookPopMenu) : RecyclerView.Adapter<BookCacheItemAdapter.VH>() {

    private val list = ArrayList<BookDb>()

    fun setData(newList:List<BookDb>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class VH(private val binding: BooksCacheItemBinding):RecyclerView.ViewHolder(binding.root){

        fun onBind(bookDb: BookDb){
            binding.authorTv.text = bookDb.author
            binding.isbnTv.text = bookDb.isbn
            binding.titleTv.text = bookDb.title
            binding.dateTv.text = DateProvider.Base().getFormattedDate()

            binding.container.setOnClickListener {
                bookPopMenu.show(binding.container,bookDb)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(BooksCacheItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}