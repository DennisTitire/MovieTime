package com.example.movietime.navigation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movietime.LoginActivity
import com.example.movietime.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        emailOut.text = auth.currentUser?.email
        signOutMethod()
    }

    private fun signOutMethod() {
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
        }
    }



}