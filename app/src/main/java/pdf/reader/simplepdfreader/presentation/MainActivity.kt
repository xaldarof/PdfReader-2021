package pdf.reader.simplepdfreader.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.bind
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val coreFragment = CoreFragment()
        val favoriteFragment = FavoriteFragment()
        val interestingFragment = InterestingFragment()
        val willReadFragment = WillReadFragment()
        val doneFragment = DoneFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container,coreFragment).commit()

        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        toggle.syncState()
        binding.drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarMain.menuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.START)
        }
        
        binding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.coreFragment -> navigation(coreFragment)
                R.id.interestingFragment -> navigation(interestingFragment)
                R.id.willReadFragment -> navigation(willReadFragment)
                R.id.doneState -> navigation(doneFragment)
                R.id.favoriteFragment -> navigation(favoriteFragment)
            }
            true
        }
    }
    @SuppressLint("WrongConstant")
    private fun navigation(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
        binding.drawerLayout.closeDrawer(Gravity.START)
    }
}