package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import android.R
import android.os.*
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModel
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModelFactory
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer

class CoreFragment : Fragment(), ItemAdapter.OnClickListener {

    private lateinit var binding: FragmentCoreBinding
    private lateinit var viewModel: CoreFragmentViewModel
    private lateinit var myPdfRenderer: MyPdfRenderer
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPdfRenderer = MyPdfRenderer(requireContext())
        linearLayoutManager = LinearLayoutManager(requireContext(),VERTICAL,false)
        binding.rv.layoutManager = linearLayoutManager

        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        viewModel = ViewModelProvider(this, CoreFragmentViewModelFactory(requireContext()))
            .get(CoreFragmentViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            updateData()
        }

        binding.rv.adapter = itemAdapter
        viewModel.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())

    }

    private suspend fun updateData(){
        while (true) {
            delay(2500)
            viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
                itemAdapter.setData(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())
        CoroutineScope(Dispatchers.Main).launch {
            updateData()
        }
    }

    @KoinApiExtension
    override fun onClick(pdfFileDb: PdfFileDb) {
        val intent = Intent(context,ReadingActivity::class.java)
        intent.putExtra("pdf",PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
        startActivity(intent)
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