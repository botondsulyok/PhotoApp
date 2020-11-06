package hu.bme.photoapp.competitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.categories.CategoryRepository

class CompetitionViewModel : ViewModel() {

    private val repository: CompetitionRepository

    val allCompetitions: LiveData<MutableList<Competition>> = MutableLiveData<MutableList<Competition>>()

    init {
        repository = CompetitionRepository()
        repository.getAllCompetitions(this::addCompetitions, this::showError)
    }

    private fun addCompetitions(competitions: List<Competition>) {
        allCompetitions.value?.addAll(competitions)
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}