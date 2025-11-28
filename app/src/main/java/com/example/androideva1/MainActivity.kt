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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayoutUsername: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewCreateAccount: TextView
    private lateinit var textViewRecoverPassword: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referencias a la vista (ajusta ids si tus nombres son distintos)
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount)
        textViewRecoverPassword = findViewById(R.id.textViewRecoverPassword)

        // Limpiar errores al escribir
        editTextUsername.doAfterTextChanged { textInputLayoutUsername.error = null }
        editTextPassword.doAfterTextChanged { textInputLayoutPassword.error = null }

        // Botón Iniciar sesión
        buttonLogin.setOnClickListener {
            if (validateLoginForm()) {
                val email = editTextUsername.text?.toString()?.trim().orEmpty()
                val password = editTextPassword.text?.toString()?.trim().orEmpty()

                signInWithFirebase(email, password)
            }
        }

        // Crear cuenta
        textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Recuperar contraseña (de momento sigue yendo a tu activity de recuperar)
        textViewRecoverPassword.setOnClickListener {
            startActivity(Intent(this, RecoverActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        // Si ya hay usuario logueado, ir directo al Home (opcional pero bonito)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHome()
        }
    }

    private fun validateLoginForm(): Boolean {
        var isValid = true

        val email = editTextUsername.text?.toString()?.trim().orEmpty()
        val password = editTextPassword.text?.toString()?.trim().orEmpty()

        if (email.isEmpty()) {
            textInputLayoutUsername.error = "Ingrese su correo"
            isValid = false
        }

        if (password.isEmpty()) {
            textInputLayoutPassword.error = "Ingrese su contraseña"
            isValid = false
        }

        return isValid
    }

    private fun signInWithFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    goToHome()
                } else {
                    val message = task.exception?.localizedMessage
                        ?: "Error al iniciar sesión"
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        // opcional: limpiar el backstack para que no vuelva al login con atrás
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
