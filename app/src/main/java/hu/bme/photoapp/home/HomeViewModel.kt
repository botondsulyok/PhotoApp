package hu.bme.photoapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
        repository.getImagesByCategory(id, this::filterImages, this::showError)
    }

    private fun addImages(images: List<Image>) {
        allImages.postValue(images.toMutableList())
    }

    private fun filterImages(images: List<Image>) {
        allImages.value?.clear()
        allImages.postValue(images.toMutableList())
    }

    fun likeImage(id: String, value: String, onSuccess: () -> Unit) {
        repository.likeImage(id, value, onSuccess, this::showError)
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}