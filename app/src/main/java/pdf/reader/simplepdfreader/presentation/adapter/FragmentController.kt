package pdf.reader.simplepdfreader.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import java.lang.ref.WeakReference

@SuppressLint("UseCompatLoadingForDrawables")
class FragmentController(
    private val activity: AppCompatActivity,
    private val pdfFilesRepository: PdfFilesRepository,
    private val fragments: List<Fragment>
) {

    private var viewPager2: ViewPager2 = activity.findViewById(R.id.pager)
    private var tabLayout: TabLayout

    private var fragmentAdapter: FragmentAdapter =
        FragmentAdapter(
            activity.supportFragmentManager,
            activity.lifecycle, fragments
        )
    private var badgeDrawable: BadgeDrawable
    private var badgeDrawableNew:BadgeDrawable

    init {
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        tabLayout = activity.findViewById(R.id.tab_layout)
        viewPager2.adapter = fragmentAdapter
        viewPager2.offscreenPageLimit = 6

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon =
                        activity.resources.getDrawable(R.drawable.ic_baseline_home_24)
                }

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

        badgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
        badgeDrawable.backgroundColor = Color.GRAY
        badgeDrawable.isVisible = true

        badgeDrawableNew = tabLayout.getTabAt(2)!!.orCreateBadge
        badgeDrawableNew.backgroundColor = Color.RED
        badgeDrawableNew.isVisible = true

        CoroutineScope(Dispatchers.Main).launch {
            dataCountObserve()
            dataCountObserveNew()
        }
    }

    private suspend fun dataCountObserve() {
        pdfFilesRepository.fetchDataForCount().observe(activity, {
            badgeDrawable.number = it.size
        })
    }
    private fun dataCountObserveNew(){
        pdfFilesRepository.fetchNewPdfFiles().asLiveData().observe(activity,{
            if (it.isEmpty()){
                badgeDrawableNew.isVisible = false
            }else {
                badgeDrawableNew.isVisible = true
                badgeDrawableNew.number = it.size
            }
        })
    }
}