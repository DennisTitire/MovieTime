package com.example.movietime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.toolbar.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
        register()
    }

    private fun register() {
        // handle the button click -> calling the firebase API
        registerButton.setOnClickListener {
            // validation for EditText
            if (TextUtils.isEmpty(emailInput.text.toString())) {
                emailInput.setError("Please enter your email")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter your password")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@RegistrationActivity, "Registration Success", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
}