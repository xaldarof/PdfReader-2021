package pdf.reader.simplepdfreader.presentation.fragments.net_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.core.Status
import pdf.reader.simplepdfreader.data.room.BookDb
import pdf.reader.simplepdfreader.databinding.FragmentSearchBookBinding
import pdf.reader.simplepdfreader.presentation.BookInfoActivity
import pdf.reader.simplepdfreader.presentation.dialogs.BookPopMenu
import pdf.reader.simplepdfreader.presentation.dialogs.ISBNDialog
import pdf.reader.simplepdfreader.presentation.viewmodels.SearchActivityViewModel

@KoinApiExtension
class SearchBookFragment : Fragment() ,KoinComponent, BookPopMenu.Base.CustomPopupCallBack {

    private lateinit var binding : FragmentSearchBookBinding
    private val viewModel: SearchActivityViewModel by inject()
    private val dialog = ISBNDialog.Base()
    private lateinit var pager : ViewPager2
    private val popup = BookPopMenu.Base().registerCallBack(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSearchBookBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = requireActivity().findViewById(R.id.viewPager)
        binding.progress.visibility = View.INVISIBLE

        binding.infoBtn.setOnClickListener { dialog.show(requireContext()) }
        binding.backBtn.setOnClickListener { requireActivity().finish() }
        binding.historyBtn.setOnClickListener { pager.currentItem++ }

        binding.searchBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE

            val isbn = binding.isbnEditText.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                val book = viewModel.fetchBooks(isbn)
                when (book.status) {
                    Status.SUCCESS -> {
                        binding.progress.visibility = View.INVISIBLE
                        val intent = Intent(requireContext(), BookInfoActivity::class.java)
                        intent.putExtra("book", book.data)
                        startActivity(intent)
                        viewModel.insertBookCache(BookDb(book.data!!.title,book.data.author,isbn))

                    }
                    Status.ERROR -> {
                        binding.progress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Данные не найдены", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {}
                }
            }
        }

    }

    override fun sendResult(result: String) {
        pager.currentItem = 0
        binding.isbnEditText.setText(result)
        Log.d("pos","CALLBACK")
    }
}