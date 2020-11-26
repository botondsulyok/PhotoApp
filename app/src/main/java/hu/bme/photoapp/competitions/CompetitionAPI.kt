package hu.bme.photoapp.competitions

import hu.bme.photoapp.model.MainActivityViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CompetitionAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }


    @GET("competitions")
    fun getCompetitions(@Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token): Call<List<Competition>>

}