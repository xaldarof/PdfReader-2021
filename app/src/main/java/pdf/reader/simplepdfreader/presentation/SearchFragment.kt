package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentSearchBinding
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.domain.SearchFragmentViewModel
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity
import pdf.reader.simplepdfreader.tools.*


@KoinApiExtension
class SearchFragment : Fragment(), ItemAdapter.OnClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myPdfRenderer = MyPdfRenderer(requireContext())
        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        binding.rv.itemAnimator = null
        updateData()
        binding.rv.adapter = itemAdapter

        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.searchEditText.addTextChangedListener(TextWatcher(object : TextWatcherWrapper {
            override fun onTextChange(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
                    val searchFilterManager = SearchFilterManager.Base(it, itemAdapter)
                    searchFilterManager.filter(p0.toString())
                })
            }
        }))
    }

    private fun updateData() {
        viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
            itemAdapter.setData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    override fun onClick(pdfFileDb: PdfFileDb) {
        NextActivity.Base(requireContext())
            .startActivity(PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
    }

    override fun onClickAddToFavorites(pdfFileDb: PdfFileDb) {
        viewModel.updateFavoriteState(pdfFileDb)
    }

    override fun onClickAddTooInteresting(pdfFileDb: PdfFileDb) {
        viewModel.updateInterestingState(pdfFileDb)
    }

    override fun onClickAddToWillRead(pdfFileDb: PdfFileDb) {
        viewModel.updateWillReadState(pdfFileDb)
    }

    override fun onClickAddToFinished(pdfFileDb: PdfFileDb) {
        viewModel.updateFinishedState(pdfFileDb)
    }
}