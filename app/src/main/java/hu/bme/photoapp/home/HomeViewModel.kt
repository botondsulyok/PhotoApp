package hu.bme.photoapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository

    val allImages: MutableLiveData<MutableList<Image>> = MutableLiveData<MutableList<Image>>()

    init {
        repository = HomeRepository()
        repository.getAllImages(this::addImages, this::showError)
    }

    private fun addImages(images: List<Image>) {
        allImages.postValue(images.toMutableList())
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}