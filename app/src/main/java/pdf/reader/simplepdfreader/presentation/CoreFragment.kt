package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import android.R
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import pdf.reader.simplepdfreader.data.cache.RecyclerViewPosition
import pdf.reader.simplepdfreader.data.cache.RecyclerViewPositionImpl
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
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerViewPosition: RecyclerViewPosition

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
        sharedPreferences = requireActivity().getSharedPreferences("cache",MODE_PRIVATE)
        recyclerViewPosition = RecyclerViewPosition(RecyclerViewPositionImpl(sharedPreferences))
        linearLayoutManager = LinearLayoutManager(requireContext(),VERTICAL,false)
        binding.rv.layoutManager = linearLayoutManager

        itemAdapter = ItemAdapter(this, myPdfRenderer, binding.rv, requireContext())
        viewModel = ViewModelProvider(this, CoreFragmentViewModelFactory(requireContext()))
            .get(CoreFragmentViewModel::class.java)

        updateData()

        binding.rv.adapter = itemAdapter

        viewModel.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())
        requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

    }

    private fun updateData(){
        viewModel.fetchPdfFiles().observe(viewLifecycleOwner, {
            itemAdapter.setData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        binding.rv.scrollToPosition(recyclerViewPosition.read())
        updateData()
    }
    override fun onPause() {
        super.onPause()
        val lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
        recyclerViewPosition.save(lastPosition)
    }

    override fun onClick(pdfFileDb: PdfFileDb) {
        val intent = Intent(context,ReadingActivity::class.java)
        intent.putExtra("pdf",PdfFileDbToPdfFileMapper.Base().map(pdfFileDb))
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        //requireActivity().finish()

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