package com.example.movietime.navigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietime.DataMovies
import com.example.movietime.R
import com.example.movietime.RecyclerviewAdapter

class HomeFragment : Fragment() {

    private lateinit var adapter: RecyclerviewAdapter
    val listData: ArrayList<DataMovies> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        buildDisplayData()
        initRecyclerview(view)
        return view
    }

    private fun buildDisplayData() {
        listData.add(DataMovies("Jurassic Park", "A pragmatic paleontologist touring an almost complete theme park on an island in Central America is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.", "Steven Spielberg", "Adventure"))
        listData.add(DataMovies("Titanic", "The Titanic was a luxury British steamship that sank in the early hours of April 15, 1912 after striking an iceberg, leading to the deaths of more than 1,500 passengers and crew.", "James Cameron", "Drama"))
        listData.add(DataMovies("The Matrix", "The film describes a future in which reality perceived by humans is actually the Matrix, a simulated reality created by sentient Machines in order to pacify and subdue the human population.", "Andy and Lana Wachowskin", "Action, Science fiction"))
        listData.add(DataMovies("Toy Story", "Toy Story is about the 'secret life of toys' when people are not around. When Buzz Lightyear, a space-ranger, takes Woody's place as Andy's favorite toy, Woody doesn't like the situation and gets into a fight with Buzz. ... A toy named Woody has it all.", "John Lasseter", "Comedy, Animation"))
        listData.add(DataMovies("Star Wars", "One of the central themes in Star Wars is the idea that one person in the right place at the right time doing the right thing can bring down an entire system. The explosion of the Death Star is a collective undertaking, but ultimately comes down to just one person—Luke Skywalker—hitting the bullseye.", "George Lucas", "Science fiction, Action"))
        listData.add(DataMovies("The Godfather", "The Godfather is set in the 1940s and takes place entirely within the world of the Corleones, a fictional New York Mafia family. It opens inside the dark office of the family patriarch, Don Vito Corleone (also known as the Godfather and played by Brando), on the wedding day of his daughter, Connie (Talia Shire).", "Francis Ford Coppola", "Drama"))
        listData.add(DataMovies("The Fast and the Furious", "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.", "Rob Cohen", "Action"))
        listData.add(DataMovies("A Dog's Purpose", "A dog looks to discover his purpose in life over the course of several lifetimes and owners.", "Lasse Hallström", "Adventure"))
        listData.add(DataMovies("No Time to Die", "James Bond has left active service. His peace is short-lived when Felix Leiter, an old friend from the CIA, turns up asking for help, leading Bond onto the trail of a mysterious villain armed with dangerous new technology", "Cary Joji Fukunaga", "Action"))
        listData.add(DataMovies("The Terminator", "A human soldier is sent from 2029 to 1984 to stop an almost indestructible cyborg killing machine, sent from the same year, which has been programmed to execute a young woman whose unborn son is the key to humanity's future salvation.", "James Cameron", "Action"))
    }

    private fun initRecyclerview(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerviewAdapter(listData)
        recyclerView.adapter = adapter
    }


}