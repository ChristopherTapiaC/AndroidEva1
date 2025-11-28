package com.example.androideva1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class AddNewsActivity : AppCompatActivity() {

    private lateinit var tilTitle: TextInputLayout
    private lateinit var tilDescription: TextInputLayout
    private lateinit var tilImageUrl: TextInputLayout
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        // Referencias a vistas
        tilTitle = findViewById(R.id.tilTitle)
        tilDescription = findViewById(R.id.tilDescription)
        tilImageUrl = findViewById(R.id.tilImageUrl)
        btnSave = findViewById(R.id.btnSaveNews)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            guardarNoticia()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun guardarNoticia() {
        tilTitle.error = null
        tilDescription.error = null

        val titulo = tilTitle.editText?.text.toString().trim()
        val descripcion = tilDescription.editText?.text.toString().trim()
        val imageUrl = tilImageUrl.editText?.text.toString().trim()

        // Validaciones básicas
        var hasError = false

        if (titulo.isEmpty()) {
            tilTitle.error = "Ingresa un título"
            hasError = true
        }
        if (descripcion.isEmpty()) {
            tilDescription.error = "Ingresa una descripción"
            hasError = true
        }

        if (hasError) return

        // Mapa para Firestore
        val data = mutableMapOf<String, Any>(
            "titulo" to titulo,
            "descripcion" to descripcion
        )

        // URL imagen opcional
        if (imageUrl.isNotEmpty()) {
            data["imagen"] = imageUrl
        }

        db.collection("noticias")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Noticia creada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
