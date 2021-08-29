package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModel
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModelFactory
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import pdf.reader.simplepdfreader.tools.NextActivity

class CoreFragment : Fragment(), ItemAdapter.OnClickListener {

    private lateinit var binding: FragmentCoreBinding
    private lateinit var viewModel: CoreFragmentViewModel
    private lateinit var myPdfRenderer: MyPdfRenderer
    private lateinit var itemAdapter: ItemAdapter
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myPdfRenderer = MyPdfRenderer(requireContext())
        binding = FragmentCoreBinding.inflate(inflater, container, false)

        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        viewModel = ViewModelProvider(this, CoreFragmentViewModelFactory(requireContext()))
            .get(CoreFragmentViewModel::class.java)
        binding.rv.adapter = itemAdapter

        viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
            itemAdapter.update(it, position)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())

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