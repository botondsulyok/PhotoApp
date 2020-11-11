package hu.bme.photoapp.home

import android.util.Log
import hu.bme.photoapp.model.MainActivityViewModel
import hu.bme.photoapp.photo.Comment
import hu.bme.photoapp.photo.CommentContainer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeRepository {

    private val imageAPI: ImageAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ImageAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.imageAPI = retrofit.create(ImageAPI::class.java)
    }

    fun getAllImages(onSuccess: (List<Image>) -> Unit,
                     onError: (Throwable) -> Unit) {
        val getImagesRequest = imageAPI.getImages()

        getImagesRequest.enqueue(object: Callback<List<Image>> {
            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                onError(t)
            }
            override fun onResponse(
                call: Call<List<Image>>,
                response: Response<List<Image>>
            ) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }

    fun getImage(id: String,
                 onSuccess: (Image) -> Unit,
                 onError: (Throwable) -> Unit
                 ) {
        val getImageRequest = imageAPI.getImage(id)

        getImageRequest.enqueue(object: Callback<Image> {
            override fun onFailure(call: Call<Image>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                response.body()?.let { onSuccess(it) }
            }

        })
    }

    fun getImagesByCategory(id: String,
                            onSuccess: (List<Image>) -> Unit,
                            onError: (Throwable) -> Unit
    ) {
        val getImagesByCategoryRequest = imageAPI.getImagesByCategory(id)

        getImagesByCategoryRequest.enqueue(object: Callback<List<Image>> {
            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                onError(t)
            }
            override fun onResponse(
                call: Call<List<Image>>,
                response: Response<List<Image>>
            ) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }

    fun likeImage(id: String,
                  value: String,
                  onSuccess: () -> Unit,
                  onError: (Throwable) -> Unit
    ) {
        val likeImageRequest = imageAPI.likeImage(id = id, value = value)

        likeImageRequest.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

    fun getAllComments(id: String,
                       onSuccess: (CommentContainer) -> Unit,
                       onError: (Throwable) -> Unit
    ) {
        val getCommentsRequest = imageAPI.getComments(id)

        getCommentsRequest.enqueue(object: Callback<CommentContainer> {
            override fun onFailure(call: Call<CommentContainer>, t: Throwable) {
                onError(t)
            }
            override fun onResponse(
                call: Call<CommentContainer>,
                response: Response<CommentContainer>
            ) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }

    fun postComment(id: String,
                    text: String,
                  onSuccess: () -> Unit,
                  onError: (Throwable) -> Unit
    ) {
        val postCommentRequest = imageAPI.postComment(id = id, value = text)

        postCommentRequest.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

}
