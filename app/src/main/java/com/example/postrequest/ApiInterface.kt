package com.example.postrequest

import retrofit2.Call
import retrofit2.http.GET
import com.example.postrequest.model.PersonModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @GET("test/")
    fun getData(): Call<PersonModel>

    @POST("test/")
    fun addData(@Body person: Person): Call<Person>
}