package com.example.postrequest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequest.databinding.ActivityMainBinding
import com.example.postrequest.model.PersonModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://dojo-recipes.herokuapp.com/"
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var people: ArrayList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set UI
        supportActionBar!!.hide()
        people = arrayListOf()
        getApiData()

        binding.sendButton.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val location = binding.locationEt.text.toString()
            postApiData(name, location)
            getApiData()

            binding.nameEt.setText("")
            binding.locationEt.setText("")
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        recyclerview = binding.recyclerview
        recyclerview.adapter = RecyclerviewAdapter(people)
    }

    private fun postApiData(name: String, location: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        retrofitBuilder.addData(Person(name, location))
            .enqueue(object : Callback<Person> {
                override fun onResponse(call: Call<Person>, response: Response<Person>) {
                    Log.d("MainActivity", "Response: $response")
                }

                override fun onFailure(call: Call<Person>, t: Throwable) {
                    Log.d("MainActivity", "POST error: ${t.message}")

                }
            })
    }


    private fun getApiData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val data = retrofitBuilder.getData()
        data.enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                try {
                    val peopleData = response.body()!!
                    for (person in peopleData) {
                        people.add(Person(person.name, person.location))
                    }

                    setRecyclerView()

                } catch (ex: Exception) {
                    Log.d("MainActivity", "Error: ${ex.message}")
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                Log.d("MainActivity", "On failure: ${t.message}")
            }

        })

    }
}