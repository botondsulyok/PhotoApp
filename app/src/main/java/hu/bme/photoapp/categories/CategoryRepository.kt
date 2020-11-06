package hu.bme.photoapp.categories

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CategoryRepository {

    private val categoryAPI: CategoryAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(CategoryAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.categoryAPI = retrofit.create(CategoryAPI::class.java)
    }

    fun getAllCategories(onSuccess: (List<Category>) -> Unit,
                         onError: (Throwable) -> Unit) {
        val getCategoriesRequest = categoryAPI.getCategories()

        getCategoriesRequest.enqueue(object: Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                t.message?.let { Log.i("asd", it) }
                onError(t)

            }
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                onSuccess(response.body()!!)
            }
        })

    }

}
