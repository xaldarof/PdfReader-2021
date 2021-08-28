package pdf.reader.simplepdfreader.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pdf.reader.simplepdfreader.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val coreFragment = CoreFragment()
        supportFragmentManager.beginTransaction().replace(R.id.layout,coreFragment).commit()

    }
}