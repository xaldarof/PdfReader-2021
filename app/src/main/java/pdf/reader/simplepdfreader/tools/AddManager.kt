package pdf.reader.simplepdfreader.tools

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface AddManager {

    fun init()

    class Base(private val context: Context, private val addView: AdView) : AddManager {
        override fun init() {
            MobileAds.initialize(context)
                val addBuilder = AdRequest.Builder()
                    .build()
                addView.loadAd(addBuilder)
        }
    }
}