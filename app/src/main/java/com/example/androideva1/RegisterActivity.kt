package com.example.androideva1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var textInputLayoutFullName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPhone: TextInputLayout
    private lateinit var textInputLayoutAddress: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var editTextFullName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPhone: TextInputEditText
    private lateinit var editTextAddress: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText

    private lateinit var buttonCreateAccount: Button
    private lateinit var buttonBackToLogin: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // Referencias XML
        textInputLayoutFullName = findViewById(R.id.textInputLayoutFullName)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPhone = findViewById(R.id.textInputLayoutPhone)
        textInputLayoutAddress = findViewById(R.id.textInputLayoutAddress)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin)

        // Limpiar errores
        editTextFullName.doAfterTextChanged { textInputLayoutFullName.error = null }
        editTextEmail.doAfterTextChanged { textInputLayoutEmail.error = null }
        editTextPhone.doAfterTextChanged { textInputLayoutPhone.error = null }
        editTextAddress.doAfterTextChanged { textInputLayoutAddress.error = null }
        editTextPassword.doAfterTextChanged { textInputLayoutPassword.error = null }

        // Crear cuenta
        buttonCreateAccount.setOnClickListener {
            if (validateForm()) {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                createUser(email, password)
            }
        }

        // Volver al login
        buttonBackToLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val fullName = editTextFullName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val address = editTextAddress.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (fullName.isEmpty()) {
            textInputLayoutFullName.error = "Ingrese su nombre completo"
            isValid = false
        }
        if (email.isEmpty()) {
            textInputLayoutEmail.error = "Ingrese su correo"
            isValid = false
        }
        if (phone.isEmpty()) {
            textInputLayoutPhone.error = "Ingrese su teléfono"
            isValid = false
        }
        if (address.isEmpty()) {
            textInputLayoutAddress.error = "Ingrese su dirección"
            isValid = false
        }
        if (password.isEmpty()) {
            textInputLayoutPassword.error = "Ingrese una contraseña"
            isValid = false
        } else if (password.length < 6) {
            textInputLayoutPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        return isValid
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()

                    // Cerrar sesión para probar el login manual
                    auth.signOut()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    val message = task.exception?.localizedMessage ?: "Error al crear cuenta"
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
    }
}
