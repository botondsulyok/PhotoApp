package hu.bme.photoapp.categories

import hu.bme.photoapp.MainActivity
import hu.bme.photoapp.model.MainActivityAPI
import hu.bme.photoapp.model.MainActivityViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CategoryAPI {

    companion object {
        const val BASE_URL = MainActivityAPI.BASE_URL
    }


    @GET("categories")
    fun getCategories(@Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token): Call<List<Category>>

}