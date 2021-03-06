package com.example.movietime

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movietime.dto.DataUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()
        passwordCheckbox()
        register()
    }

    private fun passwordCheckbox() {
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                passwordInputRegistration.transformationMethod = HideReturnsTransformationMethod.getInstance()
            else
                passwordInputRegistration.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun register() {
        // handle the button click -> calling the firebase API
        registerButton.setOnClickListener {
            // validation for EditText
            if (TextUtils.isEmpty(emailInputRegistration.text.toString())) {
                emailInputRegistration.setError("Please enter your email")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInputRegistration.text.toString())) {
                passwordInputRegistration.setError("Please enter your password")
                return@setOnClickListener
            }
            progressBarRegistration.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(emailInputRegistration.text.toString(), passwordInputRegistration.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val dataUser = DataUsers(userNameRegistration.text.toString(), emailInputRegistration.text.toString(), phoneUserRegistration.text.toString(), passwordInputRegistration.text.toString())
                        val uid = firebaseAuth.uid
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                        databaseReference.child(uid!!).setValue(dataUser).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this@RegistrationActivity, "Registration Success with\n${emailInputRegistration.text}", Toast.LENGTH_LONG).show()
                                progressBarRegistration.visibility = View.INVISIBLE
                                finish()
                            } else {
                                Toast.makeText(this@RegistrationActivity, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
                            }
                        }
                        progressBarRegistration.visibility = View.INVISIBLE
                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
                        progressBarRegistration.visibility = View.INVISIBLE
                    }
            }
        }
    }

}