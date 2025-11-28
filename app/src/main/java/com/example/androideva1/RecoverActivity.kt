package com.example.androideva1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RecoverActivity : AppCompatActivity() {

    private lateinit var textInputLayoutRecoverEmail: TextInputLayout
    private lateinit var editTextRecoverEmail: TextInputEditText
    private lateinit var buttonRecoverPassword: Button
    private lateinit var buttonBackToLoginRecover: Button

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        // Referencias a la vista
        textInputLayoutRecoverEmail = findViewById(R.id.textInputLayoutRecoverEmail)
        editTextRecoverEmail = findViewById(R.id.editTextRecoverEmail)
        buttonRecoverPassword = findViewById(R.id.buttonRecoverPassword)
        buttonBackToLoginRecover = findViewById(R.id.buttonBackToLoginRecover)

        // Botón: Recuperar contraseña
        buttonRecoverPassword.setOnClickListener {
            val email = editTextRecoverEmail.text.toString().trim()

            if (email.isEmpty()) {
                textInputLayoutRecoverEmail.error = "Ingresa tu correo"
                return@setOnClickListener
            } else {
                textInputLayoutRecoverEmail.error = null
            }

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Hemos enviado un correo para restablecer tu contraseña.",
                        Toast.LENGTH_LONG
                    ).show()
                    // Opcional: volver al login
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "No existe una cuenta con ese correo.",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        // Botón: Volver al login
        buttonBackToLoginRecover.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
