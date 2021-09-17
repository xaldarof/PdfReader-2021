package pdf.reader.simplepdfreader.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pdf.reader.simplepdfreader.data.cloud.api.ApiService
import pdf.reader.simplepdfreader.databinding.ActivitySearchBookBinding

@KoinApiExtension
class SearchBookActivity : AppCompatActivity(),KoinComponent {

    private lateinit var binding : ActivitySearchBookBinding
    private val apiService :ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        CoroutineScope(Dispatchers.IO).launch {
            val data = apiService.fetchBookInfo("9785041525507")
            Log.d("pos","DATA = ${data.volumeInfo}")

        }
    }
}