package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import android.R
import androidx.appcompat.app.AppCompatDelegate
import pdf.reader.simplepdfreader.databinding.ActivityUpdatingBinding

class UpdatingActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityUpdatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        CoroutineScope(Dispatchers.Main).launch {
            for (i in 0 until 100) {
                binding.progress.progress = i
                delay(30)
                if (i > 30) {
                    binding.tv.text = "Поиск новых файлов..."
                }
                if (i > 70){
                    binding.tv.text = "Завершение поиска..."
                }

                if (i == 99) {
                    binding.tv.text = "Сканирование завершено !"
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Сканирование отменено !", Toast.LENGTH_SHORT).show()
    }
}