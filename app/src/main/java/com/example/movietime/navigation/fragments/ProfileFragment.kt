package com.example.movietime.navigation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.movietime.LoginActivity
import com.example.movietime.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        getDataUser()
        signOutMethod()
        deleteAccount()
    }

    private fun signOutMethod() {
        val firebaseUser = firebaseAuth.currentUser
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            Toast.makeText(requireActivity(), "User with email ${firebaseUser?.email} has been successfully log out!", Toast.LENGTH_LONG).show()
            activity?.startActivity(intent)
        }
    }

    private fun deleteAccount() {
        val uid = firebaseAuth.uid
        val firebaseUser = firebaseAuth.currentUser
        deleteAccountButton.setOnClickListener {
            progressBarProfile.visibility = View.VISIBLE
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            databaseReference.child(uid!!).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUser?.delete()?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireActivity(), "Account with ${firebaseUser.email} has been deleted!", Toast.LENGTH_LONG).show()
                            val intent = Intent(activity, LoginActivity::class.java)
                            progressBarProfile.visibility = View.INVISIBLE
                            activity?.startActivity(intent)
                        } else {
                            Toast.makeText(requireActivity(), "There is a problem with database. \nPlease try again!", Toast.LENGTH_LONG).show()
                            progressBarProfile.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    Toast.makeText(requireActivity(), "There is a problem with database. \nPlease try again!", Toast.LENGTH_LONG).show()
                    progressBarProfile.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun getDataUser() {
        val firebaseUser = firebaseAuth.currentUser
        profilePhoneNumber.text = firebaseUser?.phoneNumber
        profileUsername.text = firebaseUser?.displayName
        profileEmail.text = firebaseUser?.email
    }

}