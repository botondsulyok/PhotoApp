package hu.bme.photoapp.home

import retrofit2.Call
import retrofit2.http.GET

interface ImageAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    @GET("images")
    fun getImages(): Call<List<Image>>

    //TODO post

}