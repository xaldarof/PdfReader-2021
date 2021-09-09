package pdf.reader.simplepdfreader.presentation

import android.app.UiModeManager.MODE_NIGHT_NO
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pdf.reader.simplepdfreader.data.cloud.ReportModel
import pdf.reader.simplepdfreader.data.cloud.ReportServiceImpl
import pdf.reader.simplepdfreader.data.cloud.Reporter
import pdf.reader.simplepdfreader.databinding.ActivityReportBinding
import java.text.SimpleDateFormat
import java.util.*

class ReportActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReportBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("reports")
        val reporter = Reporter(ReportServiceImpl(firebaseDatabase,databaseReference))

        binding.sendBtn.setOnClickListener {
            val body = binding.reportBody.text.toString()
            val address = binding.addressEditText.text.toString()
            if (body.isNotEmpty() && address.isNotEmpty()) {
                val date = SimpleDateFormat("dd MMMM yyyy zzzz  HH:mm").format(Date())
                reporter.sendReport(ReportModel(body, date, address))
                Toast.makeText(this, "Спасибо за отзыв, мы постараемся решить вашу проблему !", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this, "Пожалуйста, напишите что нибудь.", Toast.LENGTH_SHORT).show()
                binding.reportBody.setHintTextColor(Color.RED)
                binding.addressEditText.setHintTextColor(Color.RED)
            }
        }

        binding.toolBarMain.backBtn.setOnClickListener { finish() }


    }
}