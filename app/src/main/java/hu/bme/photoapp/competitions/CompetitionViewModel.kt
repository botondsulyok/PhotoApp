package hu.bme.photoapp.competitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.categories.CategoryRepository

class CompetitionViewModel : ViewModel() {

    private val repository: CompetitionRepository

    val allCompetitions: MutableLiveData<MutableList<Competition>> = MutableLiveData<MutableList<Competition>>()

    init {
        repository = CompetitionRepository()
        getAllCompetitions()
    }

    fun getAllCompetitions() {
        allCompetitions.value?.clear()
        repository.getAllCompetitions(this::addCompetitions, this::showError)
    }

    private fun addCompetitions(competitions: List<Competition>) {
        allCompetitions.postValue(competitions.toMutableList())
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}