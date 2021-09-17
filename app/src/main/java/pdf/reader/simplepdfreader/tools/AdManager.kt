package pdf.reader.simplepdfreader.tools

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

interface AdManager {

    fun init()

    class Base(private val context: Context, private val addView: AdView) : AdManager {
        override fun init() {
            MobileAds.initialize(context)
                val addBuilder = AdRequest.Builder()
                    .build()
                addView.loadAd(addBuilder)
        }
    }
}