package hu.bme.photoapp.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository

    //val allCategories: LiveData<List<Category>>

    init {
        repository = HomeRepository()
        //allCategories = repository.getAllCategories()
    }

}