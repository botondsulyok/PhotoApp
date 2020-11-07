package hu.bme.photoapp.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MainActivityAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }

    @Headers("Content-Type: application/json")
    @POST("puser/signup")
    fun registerUser(@Body registerUser: RegisterUser): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("puser/login")
    fun loginUser(@Body registerUser: RegisterUser): Call<User>

}