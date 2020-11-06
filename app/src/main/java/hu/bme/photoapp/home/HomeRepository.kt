package hu.bme.photoapp.home

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
                onSuccess(response.body()!!)
            }
        })

    }


}
