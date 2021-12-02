package com.example.movietime

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        checkLogInUser()
        login()
    }

    override fun onResume() {
        checkInternetConnection()
        super.onResume()
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
            firebaseAuth.signInWithEmailAndPassword(emailInputLogIn.text.toString(), passwordInputLogIn.text.toString().trim()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(this@LoginActivity, "Login Success with email\n${emailInputLogIn.text}", Toast.LENGTH_LONG)
                            .show()
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

    private fun checkInternetConnection() {
        val loginActivity = findViewById<View>(R.id.login_activity)
        if (haveNetwork()) {
            Snackbar.make(loginActivity, "Found internet connection!", Snackbar.LENGTH_SHORT).show()
        } else {
            val builder = AlertDialog.Builder(this@LoginActivity)
            builder.setTitle("Movie Time")
            builder.setMessage("To log in to the Movie Time app you will need to connect to the internet!")
            builder.setPositiveButton("Continue") { dialogInterface, i -> onResume() }
            builder.setNegativeButton("Close the application") { dialogInterface, i -> finish() }
            builder.show()
        }
    }

    @Suppress("DEPRECATION")
    private fun haveNetwork(): Boolean {
        var haveWifi = false
        var haveMobile = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.allNetworkInfo
        for (info in networkInfo) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) haveWifi = true
            if (info.typeName.equals("MOBILE", ignoreCase = true)) if (info.isConnected) haveMobile = true
        }
        return haveMobile || haveWifi
    }

}