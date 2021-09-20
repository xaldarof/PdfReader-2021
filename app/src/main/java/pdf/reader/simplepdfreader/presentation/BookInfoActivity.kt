package pdf.reader.simplepdfreader.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import pdf.reader.simplepdfreader.data.cloud.api.Book
import pdf.reader.simplepdfreader.databinding.ActivityBookInfoBinding

class BookInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val book = intent.getSerializableExtra("book") as Book

        binding.authorTv.text = book.author
        binding.dateTv.text = book.publisherData
        binding.descriptionTv.text = book.desc
        binding.publisherTv.text = book.publisher
        binding.titleTv.text = book.title
        Picasso.get().load(book.image).into(binding.bookImageView)

        binding.toolBarMain.backBtn.setOnClickListener { finish() }

        binding.pdfBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(book.pdf)
            startActivity(intent)
        }

        binding.webBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(book.webReadLink)
            startActivity(intent)
        }
        binding.buyBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(book.buyLink)
            startActivity(intent)
        }

    }
}