package pdf.reader.simplepdfreader.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentInterestingBinding
import pdf.reader.simplepdfreader.presentation.viewmodels.InterestingFragmentViewModel
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

class InterestingFragment : Fragment(), ItemAdapter.OnClickListener {

    private lateinit var binding: FragmentInterestingBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: InterestingFragmentViewModel by viewModels()
    private val mapper = PdfFileDbToPdfFileMapper.Base()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterestingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myPdfRenderer = MyPdfRenderer(requireContext())
        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        binding.rv.itemAnimator = null
        binding.rv.adapter = itemAdapter

    }

    private fun updateData() {
        viewModel.fetchInterestingPdfFiles().observeForever{
            itemAdapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    override fun onClick(pdfFileDb: PdfFileDb) {
        NextActivity.Base(requireContext())
            .startActivity(mapper.map(pdfFileDb))
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