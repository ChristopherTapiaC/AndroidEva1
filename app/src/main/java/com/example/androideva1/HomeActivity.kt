package com.example.androideva1

import android.os.Bundle
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var rvNews: RecyclerView
    private lateinit var fabAddNews: FloatingActionButton
    private lateinit var adapter: NewsAdapter
    private val newsList = mutableListOf<News>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbarHome)
        setSupportActionBar(toolbar)

        // Referencias a la vista
        rvNews = findViewById(R.id.rvNews)
        fabAddNews = findViewById(R.id.fabAddNews)

        // Configurar RecyclerView
        adapter = NewsAdapter(newsList) { news ->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra("title", news.title)
            intent.putExtra("summary", news.summary)
            intent.putExtra("imageUrl", news.imageUrl ?: "")
            startActivity(intent)
        }

        fabAddNews.setOnClickListener {
            val intent = Intent(this, AddNewsActivity::class.java)
            startActivity(intent)
        }

        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = adapter

        cargarNoticias()
    }

    private fun cargarNoticias() {
        db.collection("noticias")
            .get()
            .addOnSuccessListener { result ->
                newsList.clear()

                for (document in result) {
                    val titulo = document.getString("titulo") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""
                    val imagen = document.getString("imagen") ?: ""

                    newsList.add(
                        News(
                            id = document.id,
                            title = titulo,
                            summary = descripcion,
                            imageUrl = imagen
                        )
                    )
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar noticias", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        cargarNoticias()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()

                // Volver al login
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}
