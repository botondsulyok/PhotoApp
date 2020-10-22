package hu.bme.photoapp.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    private val repository: CategoryRepository

    //val allCategories: LiveData<List<Category>>

    init {
        repository = CategoryRepository()
        //allCategories = repository.getAllCategories()
    }

}