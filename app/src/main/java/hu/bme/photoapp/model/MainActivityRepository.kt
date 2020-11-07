package hu.bme.photoapp.model

import hu.bme.photoapp.categories.Category
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivityRepository {

    private val mainActivityAPI: MainActivityAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivityAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.mainActivityAPI = retrofit.create(MainActivityAPI::class.java)
    }

    fun registerUser(registerUser: RegisterUser,
                     onSuccess: () -> Unit,
                     onError: (Throwable) -> Unit) {

        val registerUserRequest = mainActivityAPI.registerUser(registerUser)

        registerUserRequest.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

    fun loginUser(registerUser: RegisterUser,
                     onSuccess: (user: User) -> Unit,
                     onError: (Throwable) -> Unit) {

        val loginUserRequest = mainActivityAPI.loginUser(registerUser)

        loginUserRequest.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let { onSuccess(it) }
            }

        })
    }

}