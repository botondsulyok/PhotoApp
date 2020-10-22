package hu.bme.photoapp.competitions

import androidx.lifecycle.ViewModel

class CompetitionViewModel : ViewModel() {

    private val repository: CompetitionRepository

    //val allCompetitions: LiveData<List<Competition>>

    init {
        repository = CompetitionRepository()
        //allCompetitions = repository.getAllCompetitions()
    }

}