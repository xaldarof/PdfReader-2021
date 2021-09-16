package pdf.reader.simplepdfreader.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.core.ReadingFileRepository

@KoinApiExtension
class ReadingActivityViewModel : ViewModel(),KoinComponent {

    private val readingFileRepository : ReadingFileRepository by inject()
    private var page = 0
    private val liveData = MutableLiveData<Int>()

    fun updateLastPage(dirName:String,lastPage:Int) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updateLastPage(dirName,lastPage)
    }

    fun updateIsEverOpened(dirName: String,isEverOpened:Boolean) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updateIsEveOpened(dirName,isEverOpened)
    }

    fun updatePageCount(dirName:String,pageCount:Int) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updatePageCount(dirName,pageCount)
    }

    fun updateLastReadTime(dirName: String,lastReadTime: Long) = CoroutineScope(Dispatchers.IO).launch {
        readingFileRepository.updateLasReadTime(dirName,lastReadTime)
    }

    fun setPage(page:Int) {
        this.page = page
    }
    fun getPage():Int {
        return page
    }

    fun getPageState() : LiveData<Int> {
        return liveData
    }

    fun setPageState(page: Int){
        liveData.value = page
    }
}