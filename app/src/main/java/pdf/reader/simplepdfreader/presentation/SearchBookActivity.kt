package pdf.reader.simplepdfreader.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import pdf.reader.simplepdfreader.databinding.ActivitySearchBookBinding
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.presentation.adapter.ViewPagerAdapter
import pdf.reader.simplepdfreader.presentation.fragments.net_fragment.HistoryFragment
import pdf.reader.simplepdfreader.presentation.fragments.net_fragment.SearchBookFragment


@KoinApiExtension
class SearchBookActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivitySearchBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val list = arrayListOf(SearchBookFragment(),HistoryFragment())
        val adapter = ViewPagerAdapter(list,supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter

    }
}
