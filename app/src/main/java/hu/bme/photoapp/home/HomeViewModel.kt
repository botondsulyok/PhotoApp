package hu.bme.photoapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.competitions.Competition

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository

    val allImages: MutableLiveData<MutableList<Image>> = MutableLiveData<MutableList<Image>>()

    init {
        repository = HomeRepository()
    }

    fun getImage(id: String, onSuccess: (Image) -> Unit) {
        repository.getImage(id, onSuccess, this::showError)
    }

    fun getAllImages() {
        allImages.value?.clear()
        repository.getAllImages(this::addImages, this::showError)
    }

    fun getImagesByCategory(id: String) {
        repository.getCategory(id, this::filterImagesByCategory, this::showError)
    }

    fun getImagesByCompetition(id: String) {
        repository.getCompetition(id, this::filterImagesByCompetition, this::showError)
    }

    private fun addImages(images: List<Image>) {
        allImages.postValue(images.toMutableList())
    }

    private fun filterImagesByCategory(category: Category) {
        allImages.value?.clear()
        allImages.postValue(category.photoList.toMutableList())
    }

    private fun filterImagesByCompetition(competition: Competition) {
        allImages.value?.clear()
        allImages.postValue(competition.photoList.toMutableList())
    }

    fun likeImage(id: String, value: String, onSuccess: () -> Unit) {
        repository.likeImage(id, value, onSuccess, this::showError)
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}