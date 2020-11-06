package hu.bme.photoapp.competitions

import androidx.lifecycle.LiveData
import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.categories.CategoryAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CompetitionRepository {

    private val competitionAPI: CompetitionAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(CategoryAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.competitionAPI = retrofit.create(CompetitionAPI::class.java)
    }

    fun getAllCompetitions(onSuccess: (List<Competition>) -> Unit,
                         onError: (Throwable) -> Unit) {
        val getCompetitionsRequest = competitionAPI.getCompetitions()

        getCompetitionsRequest.enqueue(object: Callback<List<Competition>> {
            override fun onFailure(call: Call<List<Competition>>, t: Throwable) {
                onError(t)
            }
            override fun onResponse(
                call: Call<List<Competition>>,
                response: Response<List<Competition>>
            ) {
                onSuccess(response.body()!!)
            }
        })

    }

}
