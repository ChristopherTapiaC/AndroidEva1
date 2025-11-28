package com.example.androideva1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val imgDetail: ImageView = findViewById(R.id.imgDetail)
        val tvTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvDescription: TextView = findViewById(R.id.tvDetailDescription)

        // Recibir datos del intent
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("summary") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        // Asignar datos
        tvTitle.text = title
        tvDescription.text = description

        // Cargar imagen si existe, si no usar ícono default
        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail)
        } else {
            imgDetail.setImageResource(R.mipmap.ic_launcher)
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDetail)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
