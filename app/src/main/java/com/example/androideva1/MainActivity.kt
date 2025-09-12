package com.example.androideva1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayoutUsername: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewCreateAccount: TextView
    private lateinit var textViewRecoverPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a las vistas
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount)
        textViewRecoverPassword = findViewById(R.id.textViewRecoverPassword)

        // Quitar errores al escribir
        editTextUsername.doAfterTextChanged { textInputLayoutUsername.error = null }
        editTextPassword.doAfterTextChanged { textInputLayoutPassword.error = null }

        // Botón de login con validación
        buttonLogin.setOnClickListener {
            if (validateLogin()) {
                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
            }
        }

        // Navegación a Crear cuenta
        textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Navegación a Recuperar contraseña
        textViewRecoverPassword.setOnClickListener {
            startActivity(Intent(this, RecoverActivity::class.java))
        }
    }

    private fun validateLogin(): Boolean {
        var isValid = true
        val username = editTextUsername.text?.toString()?.trim().orEmpty()
        val password = editTextPassword.text?.toString()?.trim().orEmpty()

        if (username.isEmpty()) {
            textInputLayoutUsername.error = "Ingrese su usuario"
            isValid = false
        }

        if (password.isEmpty()) {
            textInputLayoutPassword.error = "Ingrese su contraseña"
            isValid = false
        }

        return isValid
    }
}