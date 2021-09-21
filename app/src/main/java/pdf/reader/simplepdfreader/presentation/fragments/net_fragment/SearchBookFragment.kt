package pdf.reader.simplepdfreader.presentation.fragments.net_fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import pdf.reader.simplepdfreader.presentation.dialogs.ISBNDialog
import pdf.reader.simplepdfreader.presentation.viewmodels.SearchActivityViewModel
import pdf.reader.simplepdfreader.tools.DateProvider

@KoinApiExtension
class SearchBookFragment : Fragment() ,KoinComponent {

    private lateinit var binding : FragmentSearchBookBinding
    private val viewModel: SearchActivityViewModel by inject()
    private val dialog = ISBNDialog.Base()
    private val dateProvider = DateProvider.Base()
    private lateinit var pager : ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSearchBookBinding.inflate(inflater,container,false)
        pager = requireActivity().findViewById(R.id.viewPager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progress.visibility = View.INVISIBLE

        binding.infoBtn.setOnClickListener { dialog.show(requireContext()) }
        binding.backBtn.setOnClickListener { requireActivity().finish() }
        binding.historyBtn.setOnClickListener { pager.currentItem++ }

        binding.searchBtn.setOnClickListener {
            val isbn = binding.isbnEditText.text.toString()
            if (isbn.isNotEmpty()) {
                binding.progress.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch {
                    val book = viewModel.fetchBooks(isbn)
                    when (book.status) {
                        Status.SUCCESS -> {
                            binding.progress.visibility = View.INVISIBLE
                            val intent = Intent(requireContext(), BookInfoActivity::class.java)
                            intent.putExtra("book", book.data)
                            startActivity(intent)
                            viewModel.insertBookCache(
                                BookDb(book.data!!.title, book.data.author, isbn, dateProvider.getDate()))

                        }
                        Status.ERROR -> {
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), "Данные не найдены", Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            }else {
                binding.isbnEditText.setHintTextColor(Color.RED)
            }
        }
    }
}