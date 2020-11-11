package hu.bme.photoapp.home

import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.photo.Comment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ImageAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }


    @GET("photos")
    fun getImages(@Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token): Call<List<Image>>

    @GET("photos/{id}")
    fun getImage(
        @Path("id") id: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Image>

    //TODO nem jó
    @Multipart
    @POST("photos/{id}")
    fun likeImage(
        @Path("id") id: String,
        @Part likeID: String = "like",
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>

    //TODO nem jó
    @GET("categories/{categoryID}/photoList")
    fun getImagesByCategory(
        @Path("categoryID") categoryID: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<List<Image>>

    //TODO post - fotó feltöltését megírni

    @GET()
    fun getComments(

        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<List<Comment>>

    @POST()
    fun postComment(
        @Body text: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>

}