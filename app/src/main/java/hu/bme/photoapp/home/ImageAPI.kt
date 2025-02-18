package hu.bme.photoapp.home

import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.competitions.Competition
import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.photo.CommentContainer
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ImageAPI {

    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
        const val MULTIPART_FORM_DATA = "multipart/form-data"
        const val PHOTO_MULTIPART_KEY_IMG = "ownImage"
    }

    @GET("photos")
    fun getImages(@Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token): Call<List<Image>>

    @GET("photos/{id}")
    fun getImage(
        @Path("id") id: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Image>

    @FormUrlEncoded
    @PATCH("photos/{id}")
    fun likeImage(
        @Path("id") id: String,
        @Field("propName") propName: String = "likes",
        @Field("value") value: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>

    @GET("categories/{categoryID}")
    fun getCategory(
        @Path("categoryID") categoryID: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Category>

    @GET("competitions/{competitionID}")
    fun getCompetition(
        @Path("competitionID") competitionID: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Competition>

    @Multipart
    @POST("photos")
    fun postPhoto(@Part ownImage: MultipartBody.Part,
                  @Part("title") title: RequestBody,
                  @Part("description") description: RequestBody,
                  @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<Image>


    @GET("photos/{id}/comment")
    fun getComments(
        @Path("id") id: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<CommentContainer>


    @FormUrlEncoded
    @PATCH("photos/{id}")
    fun postComment(
        @Path("id") id: String,
        @Field("propName") propName: String = "comment",
        @Field("value") value: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PATCH("categories/{categoryId}")
    fun addImageToCategory(
        @Path("categoryId") categoryId: String,
        @Field("propName") propName: String = "photoList",
        @Field("value") value: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PATCH("competitions/{competitionId}")
    fun addImageToCompetition(
        @Path("competitionId") competitionId: String,
        @Field("propName") propName: String = "photoList",
        @Field("value") value: String,
        @Header("Authorization") token: String = "bearer " + MainActivityViewModel.user.token
    ): Call<ResponseBody>



}