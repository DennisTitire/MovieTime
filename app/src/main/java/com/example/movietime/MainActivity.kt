package com.example.movietime

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movietime.navigation.fragments.FavouriteFragment
import com.example.movietime.navigation.fragments.HomeFragment
import com.example.movietime.navigation.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        changeFragment(HomeFragment(), "Home")

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemHome -> changeFragment(HomeFragment(), item.title.toString())
            R.id.itemFavourite -> changeFragment(FavouriteFragment(), item.title.toString())
            R.id.itemProfile -> changeFragment(ProfileFragment(), item.title.toString())
        }
        drawerLayout.closeDrawers()
        return true
    }

    private fun changeFragment(fragment: Fragment, title: String): String {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frameLayout, fragment).commit()
        supportActionBar?.title = title
        return title
    }

}