package hu.bme.photoapp.home

import hu.bme.photoapp.model.MainActivityViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ImageAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    /*@Headers(
        "Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzZEBhc2QuYXNkIiwidXNlcklkIjoiNWZhNTg4YmIwOTZjYmI0Y2MwZjEzNWUxIiwicGVybWlzc2lvbnMiOmZhbHNlLCJpYXQiOjE2MDQ2ODYwNTIsImV4cCI6MTYwNDY4OTY1Mn0.SH_jdc6vK0ypPwYYmShF5iD286WUbsa8FJc_SNPOV8o"
    )*/
    @GET("photos")
    fun getImages(@Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token): Call<List<Image>>

    @GET("photos/{id}")
    fun getImage(
        @Path("id") id: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Image>

    //TODO post - fotó feltöltése

}