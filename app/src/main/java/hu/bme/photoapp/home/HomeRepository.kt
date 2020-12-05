package hu.bme.photoapp.home

import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.competitions.Competition
import hu.bme.photoapp.photo.CommentContainer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File


class HomeRepository {

    private val imageAPI: ImageAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ImageAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.imageAPI = retrofit.create(ImageAPI::class.java)
    }

    fun getAllImages(
        onSuccess: (List<Image>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getImagesRequest = imageAPI.getImages()

        getImagesRequest.enqueue(object : Callback<List<Image>> {
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

    fun getImage(
        id: String,
        onSuccess: (Image) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getImageRequest = imageAPI.getImage(id)

        getImageRequest.enqueue(object : Callback<Image> {
            override fun onFailure(call: Call<Image>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                response.body()?.let { onSuccess(it) }
            }

        })
    }

    fun getCategory(
        id: String,
        onSuccess: (Category) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCategoryRequest = imageAPI.getCategory(id)

        getCategoryRequest.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }

    fun getCompetition(
        id: String,
        onSuccess: (Competition) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCompetitionRequest = imageAPI.getCompetition(id)

        getCompetitionRequest.enqueue(object : Callback<Competition> {
            override fun onFailure(call: Call<Competition>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(
                call: Call<Competition>,
                response: Response<Competition>
            ) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }

    fun likeImage(
        id: String,
        value: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val likeImageRequest = imageAPI.likeImage(id = id, value = value)

        likeImageRequest.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

    fun addImageToCategory( categoryId: String,
                            imageId: String,
                            onSuccess: () -> Unit,
                            onError: (Throwable) -> Unit
    ) {
        val addImageToCategoryRequest = imageAPI.addImageToCategory(categoryId = categoryId, value = imageId)

        addImageToCategoryRequest.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

    fun getAllComments(
        id: String,
        onSuccess: (CommentContainer) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val getCommentsRequest = imageAPI.getComments(id)

        getCommentsRequest.enqueue(object : Callback<CommentContainer> {
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

    fun postComment(
        id: String,
        text: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val postCommentRequest = imageAPI.postComment(id = id, value = text)

        postCommentRequest.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess()
            }

        })
    }

    fun postPhoto(
        filePath: String,
        title: String,
        description: String,
        onSuccess: (Image) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val file = File(filePath)

        val nameParam = title.toRequestBody(MultipartBody.FORM)

        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "ownImage",
            file.name,
            file.asRequestBody(("image/" + file.extension).toMediaTypeOrNull())
        )

        val descriptionParam = description.toRequestBody(MultipartBody.FORM)
        val uploadImageRequest = imageAPI.postPhoto(filePart, nameParam, descriptionParam)


        uploadImageRequest.enqueue(object : Callback<Image> {
            override fun onFailure(call: Call<Image>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                response.body()?.let { onSuccess(it) }
            }
        })
    }
}
