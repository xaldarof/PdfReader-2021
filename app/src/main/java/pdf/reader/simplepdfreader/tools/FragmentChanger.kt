package pdf.reader.simplepdfreader.tools

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.presentation.SearchFragment
import java.lang.ref.WeakReference

interface FragmentChanger {
    fun replace(fragment:Fragment)

    class Base(private val weakReference: WeakReference<AppCompatActivity>) : FragmentChanger {
        override fun replace(fragment: Fragment) {
            val fragment: Fragment = SearchFragment()
            val fragmentManager: FragmentManager = weakReference.get()!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }
}