package pdf.reader.simplepdfreader.presentation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.component.KoinApiExtension
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.room.PdfFileDb
import pdf.reader.simplepdfreader.presentation.CoreFragment
import pdf.reader.simplepdfreader.presentation.MainActivity
import pdf.reader.simplepdfreader.tools.ItemAdapterWrapper

@SuppressLint("UseCompatLoadingForDrawables")
class FragmentController(activity: AppCompatActivity) {

    private var viewPager2: ViewPager2 = activity.findViewById(R.id.pager)
    private var tabLayout: TabLayout
    private var fragmentAdapter: FragmentAdapter =
        FragmentAdapter(activity.supportFragmentManager, activity.lifecycle)

    init {
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        tabLayout = activity.findViewById(R.id.tab_layout)
        viewPager2.adapter = fragmentAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.icon = activity.resources.getDrawable(R.drawable.ic_baseline_home_24)
                1 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_turned_in_not_24)
                2 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_local_fire_department_24)
                3 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_access_time_24)
                4 -> tab.icon = activity.resources.getDrawable(R.drawable.ic_baseline_done_all_24)
            }
        }.attach()
    }
}