package pdf.reader.simplepdfreader.domain

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavoriteFragmentViewModelFactory: ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java)){
            return FavoriteFragmentViewModel() as T
        }
        throw IllegalArgumentException("error")
    }
}