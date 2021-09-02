package pdf.reader.simplepdfreader.presentation.adapter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import pdf.reader.simplepdfreader.R

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
                0 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_home_24)


                1 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_turned_in_not_24)


                2 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_fiber_new_24)


                3 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_local_fire_department_24)


                4 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_access_time_24)


                5 -> tab.icon =
                    activity.resources.getDrawable(R.drawable.ic_baseline_done_all_24)

            }
        }.attach()
    }
}