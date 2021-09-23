package pdf.reader.simplepdfreader.presentation.fragments.net_fragment

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.viewpager2.widget.ViewPager2
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cloud.BookRepository
import pdf.reader.simplepdfreader.data.room.BookDb
import pdf.reader.simplepdfreader.databinding.FragmentHistoryBinding
import pdf.reader.simplepdfreader.presentation.adapter.BookCacheItemAdapter
import pdf.reader.simplepdfreader.presentation.dialogs.BookPopMenu
import pdf.reader.simplepdfreader.presentation.viewmodels.HistoryFragmentViewModel

@KoinApiExtension
class HistoryFragment : Fragment(),KoinComponent,BookPopMenu.Base.OnClickCallBack {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModels : HistoryFragmentViewModel = get()
    private val bookRepository:BookRepository by inject()
    private lateinit var viewPager2: ViewPager2
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        viewPager2 = requireActivity().findViewById(R.id.viewPager)
        editText = requireActivity().findViewById(R.id.isbnEditText)

        val bookPopMenu = BookPopMenu.Base(this)
        val adapter = BookCacheItemAdapter(bookPopMenu)
        binding.rv.adapter = adapter

        binding.toolBarMain.backBtn.setOnClickListener { viewPager2.currentItem-- }

        viewModels.fetchBooksCache().observeForever {
            adapter.setData(it)
        }

        return binding.root
    }

    override suspend fun onClickDelete(bookDb: BookDb) {
       bookRepository.deleteBookCache(bookDb)
    }

    override fun onClickOpen(isbn: String) {
        viewPager2.currentItem = 0
        editText.setText(isbn)
    }
}
