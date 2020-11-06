package hu.bme.photoapp.categories

import retrofit2.Call
import retrofit2.http.GET

interface CategoryAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    @GET("categories")
    fun getCategories(): Call<List<Category>>

}