package pdf.reader.simplepdfreader.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentInterestingBinding
import pdf.reader.simplepdfreader.presentation.viewmodels.NewFragmentViewModel
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

@KoinApiExtension
class NewPdfFilesFragment : Fragment(), ItemAdapter.OnClickListener, KoinComponent {

    private lateinit var binding: FragmentInterestingBinding
    private val viewModel: NewFragmentViewModel by viewModels()
    private lateinit var itemAdapter: ItemAdapter
    private val mapper = PdfFileDbToPdfFileMapper.Base()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        updateData()

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
        viewModel.fetchNewPdfFiles().observeForever {
            itemAdapter.setData(it)
        }
    }


    override fun onResume() {
        super.onResume()
        updateData()
        Log.d("pos", "RESUME")
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