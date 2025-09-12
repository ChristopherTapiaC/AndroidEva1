package com.example.androideva1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RecoverActivity : AppCompatActivity() {

    private lateinit var textInputLayoutRecoverEmail: TextInputLayout
    private lateinit var editTextRecoverEmail: TextInputEditText
    private lateinit var buttonRecoverPassword: Button
    private lateinit var buttonBackToLoginRecover: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)

        // Referencias
        textInputLayoutRecoverEmail = findViewById(R.id.textInputLayoutRecoverEmail)
        editTextRecoverEmail = findViewById(R.id.editTextRecoverEmail)
        buttonRecoverPassword = findViewById(R.id.buttonRecoverPassword)
        buttonBackToLoginRecover = findViewById(R.id.buttonBackToLoginRecover)

        // Quitar error al escribir
        editTextRecoverEmail.doAfterTextChanged { textInputLayoutRecoverEmail.error = null }

        // Botón Recuperar
        buttonRecoverPassword.setOnClickListener {
            if (validateRecoverForm()) {
                Toast.makeText(this, "Se ha enviado un correo de recuperación", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Volver
        buttonBackToLoginRecover.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun validateRecoverForm(): Boolean {
        var isValid = true
        val email = editTextRecoverEmail.text?.toString()?.trim().orEmpty()

        if (email.isEmpty()) {
            textInputLayoutRecoverEmail.error = "Ingrese su correo electrónico"
            isValid = false
        }

        return isValid
    }
}