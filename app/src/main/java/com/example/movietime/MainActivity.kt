package com.example.movietime

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movietime.navigation.fragments.FavouriteFragment
import com.example.movietime.navigation.fragments.PopularMoviesFragment
import com.example.movietime.navigation.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        initNavigationDrawer()
        navigationItemSelected()
        changeFragment(PopularMoviesFragment(), "Home")

    }

    private fun initNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun navigationItemSelected() {
        navigationView.setNavigationItemSelectedListener {
            // it.isChecked = true
            when (it.itemId) {
                R.id.itemHome -> changeFragment(PopularMoviesFragment(), it.title.toString())
                R.id.itemFavourite -> changeFragment(FavouriteFragment(), it.title.toString())
                R.id.itemProfile -> changeFragment(ProfileFragment(), it.title.toString())
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true

        return onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Fragment, title: String): String {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frameLayout, fragment).commit()
        supportActionBar?.title = title
        return title
    }


}