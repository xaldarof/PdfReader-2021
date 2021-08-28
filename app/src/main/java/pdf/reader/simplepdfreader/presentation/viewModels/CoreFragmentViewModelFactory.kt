package pdf.reader.simplepdfreader.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CoreFragmentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoreFragmentViewModel::class.java)){
            return CoreFragmentViewModel(context) as T
        }
        throw IllegalArgumentException("error")
    }

}