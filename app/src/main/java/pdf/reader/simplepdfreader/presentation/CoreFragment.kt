package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.presentation.adapter.ItemAdapter
import pdf.reader.simplepdfreader.presentation.viewModels.CoreFragmentViewModel
import pdf.reader.simplepdfreader.presentation.viewModels.CoreFragmentViewModelFactory
import pdf.reader.simplepdfreader.tools.MyPdfRenderer

class CoreFragment : Fragment(), ItemAdapter.OnClickListener{

    private lateinit var binding: FragmentCoreBinding
    private lateinit var viewModelProvider: CoreFragmentViewModel
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var myPdfRenderer: MyPdfRenderer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myPdfRenderer = MyPdfRenderer(requireContext())
        binding = FragmentCoreBinding.inflate(inflater, container, false)
        itemAdapter = ItemAdapter(this,myPdfRenderer,binding.rv,requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelProvider = ViewModelProvider(this,CoreFragmentViewModelFactory(requireContext()))
            .get(CoreFragmentViewModel::class.java)
        viewModelProvider.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())

        viewModelProvider.fetchPdfFiles().observe(viewLifecycleOwner,{
            itemAdapter.update(it)
            binding.toolbarMain.searchEditText.setText(it.size.toString())

        })

        binding.rv.adapter = itemAdapter
    }

    override fun onClick(pdfFileDb: PdfFileDb) {

    }

    override fun onClickAddToFavorites(pdfFileDb: PdfFileDb) {
        viewModelProvider.updateFavoriteState(pdfFileDb)
    }

    override fun onClickAddTooInteresting(pdfFileDb: PdfFileDb) {
        viewModelProvider.updateInterestingState(pdfFileDb)
    }
}