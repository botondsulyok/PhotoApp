package hu.bme.photoapp.competitions

import retrofit2.Call
import retrofit2.http.GET

interface CompetitionAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    @GET("competitions")
    fun getCompetitions(): Call<List<Competition>>

}