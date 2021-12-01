package com.example.movietime

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        lifecycleScope.launch {
            if (NetworkHelper.isNetworkConnected(this@LoginActivity)) {
                checkLogInUser()
                login()
            } else {
                Snackbar.make(login_activity, "No network Connection!", Snackbar.LENGTH_SHORT).show()
            }
        }

        checkLogInUser()
        login()
    }

//    override fun onResume() {
//        checkInternetConnection()
//        super.onResume()
//    }

    override fun onStart() {
        //checkInternetConnection()
        super.onStart()
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


    private fun checkInternetConnection() {
        val loginActivity = findViewById<View>(R.id.login_activity)
        if (haveNetwork()) {
            Snackbar.make(loginActivity, "Found internet!", Snackbar.LENGTH_SHORT).show()
        } else {
            val builder = AlertDialog.Builder(this@LoginActivity)
            builder.setTitle("Movie Time")
            builder.setMessage("To use the Movie Time app you will need to connect to the internet!")
            builder.setPositiveButton("Try Again") {dialogInterface, i -> restartApp()}
            builder.setNegativeButton("Okay") {dialogInterface, i -> finish()}
            builder.show()
        }
    }

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

    private fun restartApp() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    }