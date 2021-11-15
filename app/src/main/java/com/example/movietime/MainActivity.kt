package com.example.movietime

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movietime.navigation.fragments.FavouriteFragment
import com.example.movietime.navigation.fragments.HomeFragment
import com.example.movietime.navigation.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        changeFragment(HomeFragment(), "Home")

        navigationView.setNavigationItemSelectedListener {
            // it.isChecked = true
            when (it.itemId) {
                R.id.itemHome -> changeFragment(HomeFragment(), it.title.toString())
                R.id.itemFavourite -> changeFragment(FavouriteFragment(), it.title.toString())
                R.id.itemProfile -> changeFragment(ProfileFragment(), it.title.toString())
            }
            drawerLayout.closeDrawers()
            true
        }

    }


    private fun changeFragment(fragment: Fragment, title: String): String {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frameLayout, fragment).commit()
        supportActionBar?.title = title
        return title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true

        return onOptionsItemSelected(item)
    }


}