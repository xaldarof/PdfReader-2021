package pdf.reader.simplepdfreader.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import pdf.reader.simplepdfreader.presentation.*

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val coreFragment = CoreFragment()
        val favoriteFragment = FavoriteFragment()
        val newPdfFilesFragment = NewPdfFilesFragment()
        val willReadFragment = WillReadFragment()
        val doneFragment = DoneFragment()
        val interestingFragment = InterestingFragment()

        if (position == 0) {
            return coreFragment
        }
        if (position == 1) {
            return favoriteFragment
        }
        if (position == 2) {
            return newPdfFilesFragment
        }
        if (position == 3) {
            return interestingFragment
        }
        if (position == 4) {
            return willReadFragment
        }
        if (position == 5){
            return doneFragment
        }

        return null!!
    }

}