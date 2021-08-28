package pdf.reader.simplepdfreader.presentation

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import pdf.reader.simplepdfreader.databinding.FragmentCoreBinding
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModel
import pdf.reader.simplepdfreader.domain.CoreFragmentViewModelFactory
import pdf.reader.simplepdfreader.domain.ItemAdapterActionsWrapper
import pdf.reader.simplepdfreader.tools.MyPdfRenderer

class CoreFragment : Fragment() {

    private lateinit var binding: FragmentCoreBinding
    private lateinit var viewModelProvider: CoreFragmentViewModel
    private lateinit var itemAdapterActionsWrapper: ItemAdapterActionsWrapper
    private lateinit var myPdfRenderer: MyPdfRenderer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myPdfRenderer = MyPdfRenderer(requireContext())
        binding = FragmentCoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelProvider = ViewModelProvider(this, CoreFragmentViewModelFactory(requireContext()))
            .get(CoreFragmentViewModel::class.java)
        viewModelProvider.findPdfFilesAndInsert(Environment.getExternalStorageDirectory())
        itemAdapterActionsWrapper = ItemAdapterActionsWrapper(viewModelProvider,requireContext())
        itemAdapterActionsWrapper.init(myPdfRenderer,binding.rv,requireContext(),viewLifecycleOwner)

    }
}