package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import android.R
import android.os.*
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.core.Status
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModel
import pdf.reader.simplepdfreader.domain.PdfFileDbToPdfFileMapper
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.tools.MyPdfRenderer
import kotlin.math.log

@KoinApiExtension
class CoreFragment : Fragment(), ItemAdapter.OnClickListener,KoinComponent {

    private lateinit var binding: FragmentCoreBinding
    private lateinit var myPdfRenderer: MyPdfRenderer
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel : CoreFragmentViewModel by viewModels()

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
        linearLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.rv.layoutManager = linearLayoutManager

        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        binding.rv.adapter = itemAdapter

        CoroutineScope(Dispatchers.Main).launch {
            updateData()
        }
    }

    private suspend fun updateData() {
        while (true) {
            viewModel.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())
            delay(2000)
            viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
                itemAdapter.setData(it)
            })
        }
}

    @KoinApiExtension
    override fun onClick(pdfFileDb: PdfFileDb) {
        val intent = Intent(context, ReadingActivity::class.java)
        intent.putExtra("pdf", PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
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