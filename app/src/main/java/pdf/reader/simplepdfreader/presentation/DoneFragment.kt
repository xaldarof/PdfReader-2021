package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentDoneBinding
import pdf.reader.simplepdfreader.domain.DoneFragmentViewModel
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

class DoneFragment : Fragment(), ItemAdapter.OnClickListener {
    private lateinit var binding: FragmentDoneBinding
    private val viewModel: DoneFragmentViewModel by viewModels()
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myPdfRenderer = MyPdfRenderer(requireContext())
        val itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        binding.rv.itemAnimator = null

        viewModel.fetchFinished().observe(viewLifecycleOwner, {
            itemAdapter.update(it)
            itemAdapter.notifyItemChanged(position)
        })
        binding.rv.adapter = itemAdapter

    }

    override fun onClick(pdfFileDb: PdfFileDb) {
        NextActivity.Base(requireContext())
            .startActivity(PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
    }

    override fun onClickAddToFavorites(pdfFileDb: PdfFileDb, position: Int) {
        viewModel.updateFavoriteState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddTooInteresting(pdfFileDb: PdfFileDb, position: Int) {
        viewModel.updateInterestingState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddToWillRead(pdfFileDb: PdfFileDb, position: Int) {
        viewModel.updateWillReadState(pdfFileDb)
        this.position = position
    }

    override fun onClickAddToFinished(pdfFileDb: PdfFileDb, position: Int) {
        viewModel.updateFinishedState(pdfFileDb)
        this.position = position
    }
}