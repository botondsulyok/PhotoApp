package hu.bme.photoapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository

    val allImages: MutableLiveData<MutableList<Image>> = MutableLiveData<MutableList<Image>>()

    init {
        repository = HomeRepository()
        repository.getAllImages(this::addImages, this::showError)




        //TODO törölni, csak teszt
        addImages(listOf<Image>(
            Image("asd", "asd", "asd", "https://images.unsplash.com/photo-1486758206125-94d07f414b1c?ixlib=rb-0.3.5&s=2bda5e189cbdf19185f03f310a88ae5b&auto=format&fit=crop&w=1950&q=80", "asd")
        ))
    }

    private fun addImages(images: List<Image>) {
        allImages.postValue(images.toMutableList())
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}