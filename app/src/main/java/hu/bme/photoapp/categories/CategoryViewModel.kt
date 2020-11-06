package hu.bme.photoapp.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.photoapp.competitions.Competition
import org.greenrobot.eventbus.EventBus

class CategoryViewModel : ViewModel() {

    private val repository: CategoryRepository

    val allCategories: MutableLiveData<MutableList<Category>> = MutableLiveData()

    init {
        repository = CategoryRepository()
        repository.getAllCategories(this::addCategories, this::showError)
    }

    private fun addCategories(categories: List<Category>) {
        allCategories.postValue(categories.toMutableList())
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}