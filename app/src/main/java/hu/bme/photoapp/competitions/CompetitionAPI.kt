package hu.bme.photoapp.competitions

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CompetitionAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    @Headers(
        "Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFiY2RAYWJjLmFiIiwidXNlcklkIjoiNWY5ZmZlZGI4NGNlYTMyOGI4NDM0NWZlIiwicGVybWlzc2lvbnMiOmZhbHNlLCJpYXQiOjE2MDQ2ODEwMDksImV4cCI6MTYwNDY4NDYwOX0.fUvTiYu6uAS9hXSUcvYwjFU6nqSH28iIY-XnYfNEG1g"
    )
    @GET("competitions")
    fun getCompetitions(): Call<List<Competition>>

}