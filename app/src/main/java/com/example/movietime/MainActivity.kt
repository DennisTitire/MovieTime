package com.example.movietime

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.movietime.navigation.fragments.PopularMoviesFragment
import com.example.movietime.navigation.fragments.ProfileFragment
import com.example.movietime.navigation.fragments.watchlist.WatchListFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

private const val POPULAR_MOVIES = "popular_movies_fragment"
private const val WATCH_LIST_FRAGMENT = "watch_list_fragment"
private const val PROFILE_FRAGMENT = "profile_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    companion object { lateinit var searchTextValGlob: String }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        initNavigationDrawer()
        navigationItemSelected()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        val search: MenuItem? = menu?.findItem(R.id.navigationSearch)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search for a movie"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val fragmentManager = supportFragmentManager
                val fragmentTransition = fragmentManager.beginTransaction()
                fragmentTransition.replace(R.id.frameLayout, SearchFragment()).commit()
                searchTextValGlob = searchView.query.toString()
                return true
            }
        })

        return true
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
                R.id.itemHome -> showPopularMovies("Home")
                R.id.itemFavourite -> showWatchListMovies("Watch list")
                R.id.itemProfile -> showProfile("Profile")
            }
            drawerLayout.closeDrawers()
            true
        }
        showPopularMovies("Home")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return true
    }

    private fun showPopularMovies(title: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(POPULAR_MOVIES)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)
        val profileFragment = supportFragmentManager.findFragmentByTag(PROFILE_FRAGMENT)
        watchListFragment?.let { transaction.hide(it) }
        profileFragment?.let { transaction.hide(it) }

        if (fragment == null) {
            transaction.add(R.id.frameLayout, PopularMoviesFragment(), POPULAR_MOVIES)
            supportActionBar?.title = title
        } else {
            transaction.show(fragment)
        }
        transaction.commit()

    }

    private fun showWatchListMovies(title: String): String {
        val transaction = supportFragmentManager.beginTransaction()

        val fragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)
        val watchListFragment = supportFragmentManager.findFragmentByTag(POPULAR_MOVIES)
        val profileFragment = supportFragmentManager.findFragmentByTag(PROFILE_FRAGMENT)

        watchListFragment?.let { transaction.hide(it) }
        profileFragment?.let { transaction.hide(it) }

        if (fragment == null) {
            transaction.add(R.id.frameLayout, WatchListFragment(), WATCH_LIST_FRAGMENT)
            supportActionBar?.title = title
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
        return title
    }

    private fun showProfile(title: String): String {
        val transaction = supportFragmentManager.beginTransaction()

        val fragment = supportFragmentManager.findFragmentByTag(PROFILE_FRAGMENT)
        val popularMoviesFragment = supportFragmentManager.findFragmentByTag(POPULAR_MOVIES)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)

        popularMoviesFragment?.let { transaction.hide(it) }
        watchListFragment?.let { transaction.hide(it) }

        if (fragment == null) {
            transaction.add(R.id.frameLayout, ProfileFragment(), PROFILE_FRAGMENT)
            supportActionBar?.title = title
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
        return title
    }

}