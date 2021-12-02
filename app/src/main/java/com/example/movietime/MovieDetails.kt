package com.example.movietime

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movietime.navigation.fragments.roomwatchlist.AppDatabase
import com.example.movietime.navigation.fragments.roomwatchlist.MovieEntity
import kotlinx.android.synthetic.main.activity_movie_details.*

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

//The id of the movie will be used to query a movie in your watchlist.
const val MOVIE_ID = "extra_movie_id"


class MovieDetails : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var addToWatchList: Button

    private var movieId = 0L
    private var movieBackdrop = ""
    private var moviePoster = ""
    private var movieTitle = ""
    private var movieRating = 0f
    private var movieReleaseDate = ""
    private var movieOverview = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieTime)
        setContentView(R.layout.activity_movie_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        addToWatchList = findViewById(R.id.add_to_watch_list)

        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        checkInternetConnection()

    }

    override fun onStart() {
        super.onStart()
        addToWatchList.setOnClickListener {
            if (getMovie(movieId) == null) {
                val entity = MovieEntity(
                    movieId,
                    movieTitle,
                    movieOverview,
                    moviePoster,
                    movieBackdrop,
                    movieRating,
                    movieReleaseDate
                )
                db.movieDao().insert(entity)
                addToWatchList.text = getString(R.string.remove_from_watch_list)
            } else {
                db.movieDao().delete(movieId)
                addToWatchList.text = getString(R.string.add_to_watch_list)
            }
        }

    }

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "movies.db")
            .allowMainThreadQueries().build()
    }

    private fun getMovie(id: Long): MovieEntity? {
        return db.movieDao().findById(id)
    }

    private fun populateDetails(extras: Bundle) {
        movieId = extras.getLong(MOVIE_ID)
        movieBackdrop = extras.getString(MOVIE_BACKDROP, "")
        moviePoster = extras.getString(MOVIE_POSTER, "")
        movieTitle = extras.getString(MOVIE_TITLE, "")
        movieRating = extras.getFloat(MOVIE_RATING, 0f)
        movieReleaseDate = extras.getString(MOVIE_RELEASE_DATE, "")
        movieOverview = extras.getString(MOVIE_OVERVIEW, "")

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280$movieBackdrop")
            .transform(CenterCrop())
            .into(backdrop)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342$moviePoster")
            .transform(CenterCrop())
            .into(poster)

        title.text = movieTitle
        rating.rating = movieRating / 2
        releaseDate.text = movieReleaseDate
        overview.text = movieOverview

        val movie = getMovie(movieId)

        if (movie == null) {
            addToWatchList.text = getString(R.string.add_to_watch_list)
        } else {
            addToWatchList.text = getString(R.string.remove_from_watch_list)
        }
    }

    private fun checkInternetConnection() {
        checkInternetConnection.setOnClickListener {
            isNetworkConnected(applicationContext)
        }
    }

    @Suppress("DEPRECATION")
    fun isNetworkConnected(context: Context): Boolean {
        var result = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = checkNetworkConnection(this, this.activeNetwork)
            } else {
                val networks = this.allNetworks
                for (network in networks) {
                    if (checkNetworkConnection(this, network)) {
                        result = true
                    }
                }
            }
        }
        return false
    }

    private fun checkNetworkConnection(connectivityManager: ConnectivityManager, network: Network?): Boolean {
        connectivityManager.getNetworkCapabilities(network)?.also {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Toast.makeText(applicationContext, "You are connected on WIFI", Toast.LENGTH_SHORT).show()
                return true
            } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Toast.makeText(applicationContext, "You are connected on MOBILE", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(applicationContext, "You are not connected to the internet", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

}

