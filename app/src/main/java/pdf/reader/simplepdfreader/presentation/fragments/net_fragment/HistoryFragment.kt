package pdf.reader.simplepdfreader.presentation.fragments.net_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import pdf.reader.simplepdfreader.databinding.FragmentHistoryBinding
import pdf.reader.simplepdfreader.presentation.adapter.BookCacheItemAdapter
import pdf.reader.simplepdfreader.presentation.viewmodels.HistoryFragmentViewModel

@KoinApiExtension
class HistoryFragment : Fragment(),KoinComponent {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModels : HistoryFragmentViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)

        val adapter = BookCacheItemAdapter()
        binding.rv.adapter = adapter

        viewModels.fetchBooksCache().observeForever {
            adapter.setData(it)

        }

        return binding.root
    }

}