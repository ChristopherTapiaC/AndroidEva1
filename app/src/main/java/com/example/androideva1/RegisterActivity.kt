package com.example.androideva1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var textInputLayoutFullName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPhone: TextInputLayout
    private lateinit var textInputLayoutAddress: TextInputLayout

    private lateinit var editTextFullName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPhone: TextInputEditText
    private lateinit var editTextAddress: TextInputEditText

    private lateinit var buttonCreateAccount: Button
    private lateinit var buttonBackToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias a los campos
        textInputLayoutFullName = findViewById(R.id.textInputLayoutFullName)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPhone = findViewById(R.id.textInputLayoutPhone)
        textInputLayoutAddress = findViewById(R.id.textInputLayoutAddress)

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextAddress = findViewById(R.id.editTextAddress)

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin)

        // Quitar errores al escribir
        editTextFullName.doAfterTextChanged { textInputLayoutFullName.error = null }
        editTextEmail.doAfterTextChanged { textInputLayoutEmail.error = null }
        editTextPhone.doAfterTextChanged { textInputLayoutPhone.error = null }
        editTextAddress.doAfterTextChanged { textInputLayoutAddress.error = null }

        // Botón Crear cuenta
        buttonCreateAccount.setOnClickListener {
            if (validateRegisterForm()) {
                Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        // Botón Volver al login
        buttonBackToLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun validateRegisterForm(): Boolean {
        var isValid = true

        val fullName = editTextFullName.text?.toString()?.trim().orEmpty()
        val email = editTextEmail.text?.toString()?.trim().orEmpty()
        val phone = editTextPhone.text?.toString()?.trim().orEmpty()
        val address = editTextAddress.text?.toString()?.trim().orEmpty()

        if (fullName.isEmpty()) {
            textInputLayoutFullName.error = "Ingrese su nombre completo"
            isValid = false
        }
        if (email.isEmpty()) {
            textInputLayoutEmail.error = "Ingrese su correo electrónico"
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

        return isValid
    }
}