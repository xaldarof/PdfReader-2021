package pdf.reader.simplepdfreader.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentFavoriteBinding
import pdf.reader.simplepdfreader.presentation.viewmodels.FavoriteFragmentViewModel
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

@KoinApiExtension
class FavoriteFragment : Fragment(), ItemAdapter.OnClickListener, KoinComponent {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteFragmentViewModel by viewModels()
    private lateinit var itemAdapter: ItemAdapter
    private val mapper = PdfFileDbToPdfFileMapper.Base()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myPdfRenderer = MyPdfRenderer(requireContext())
        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        binding.rv.itemAnimator = null
        updateData()
        binding.rv.adapter = itemAdapter

    }

    private fun updateData() {
        viewModel.fetchFavorites().observeForever {
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