package com.example.movietime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity() {
    lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        checkLogInUser()
        login()
    }

    private fun checkLogInUser() {
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun login() {
        loginButton.setOnClickListener {
            // validation for EditText
            if (TextUtils.isEmpty(emailInputLogIn.text.toString().trim())) {
                emailInputLogIn.setError("Please enter email!")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInputLogIn.text.toString().trim())) {
                passwordInputLogIn.setError("Please enter password!")
                return@setOnClickListener
            }
            firebaseAuth.signInWithEmailAndPassword(emailInputLogIn.text.toString(), passwordInputLogIn.text.toString().trim())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed, please try again!", Toast.LENGTH_LONG).show()
                    }
                }
        }
        signInButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }

}