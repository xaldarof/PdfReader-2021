package pdf.reader.simplepdfreader.presentation.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pdf.reader.simplepdfreader.presentation.*

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        val coreFragment = CoreFragment()
        val favoriteFragment = FavoriteFragment()
        val interestingFragment = InterestingFragment()
        val willReadFragment = WillReadFragment()
        val doneFragment = DoneFragment()

        if (position == 0) {
            return coreFragment
        }
        if (position == 1) {
            return favoriteFragment
        }
        if (position == 2) {
            return interestingFragment
        }
        if (position == 3) {
            return willReadFragment
        }
        if (position == 4) {
            return doneFragment
        }
        return null!!
    }

}