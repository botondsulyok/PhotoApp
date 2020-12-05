package hu.bme.photoapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.photoapp.categories.Category
import hu.bme.photoapp.competitions.Competition
import okhttp3.ResponseBody

class HomeViewModel : ViewModel() {

    private val repository: HomeRepository

    val allImagesMutableList: MutableLiveData<MutableList<Image>> = MutableLiveData<MutableList<Image>>()

    private var allImages: MutableList<Image> = mutableListOf()

    private val searchFilteredImages: MutableList<Image> = mutableListOf()

    init {
        repository = HomeRepository()
    }

    fun getImage(id: String, onSuccess: (Image) -> Unit) {
        repository.getImage(id, onSuccess, this::showError)
    }

    fun getAllImages() {
        allImagesMutableList.value?.clear()
        repository.getAllImages(this::addImages, this::showError)
    }

    fun getImagesByCategory(id: String) {
        repository.getCategory(id, this::filterImagesByCategory, this::showError)
    }

    fun getImagesByCompetition(id: String) {
        repository.getCompetition(id, this::filterImagesByCompetition, this::showError)
    }

    private fun addImages(images: List<Image>) {
        allImages = images.toMutableList()
        allImagesMutableList.value = allImages
    }

    private fun filterImagesByCategory(category: Category) {
        allImages = category.photoList.toMutableList()
        allImagesMutableList.value = allImages
    }

    private fun filterImagesByCompetition(competition: Competition) {
        allImages = competition.photoList.toMutableList()
        allImagesMutableList.value = allImages
    }

    fun likeImage(id: String, value: String, onSuccess: () -> Unit) {
        repository.likeImage(id, value, onSuccess, this::showError)
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

    fun postPhoto(
        filePath: String,
        title: String,
        description: String,
        onSuccess: (Image) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        repository.postPhoto(
            filePath = filePath,
            title = title,
            description = description,
            onSuccess = onSuccess,
            onError = onError
        )
    }

    fun addImageToCategory(categoryId: String,
                           imageId: String,
                           onSuccess: () -> Unit,
                           onError: (Throwable) -> Unit){
        repository.addImageToCategory(categoryId, imageId, onSuccess, onError)
    }

    fun addImageToCompetition(competitionId: String,
                           imageId: String,
                           onSuccess: () -> Unit,
                           onError: (Throwable) -> Unit){
        repository.addImageToCompetition(competitionId, imageId, onSuccess, onError)
    }

    fun searchFilterPhotoByTitle(
        title: String
    ) {
        searchFilteredImages.clear()
        for(image in allImages) {
            if(image.title.contains(title, true)) {
                searchFilteredImages.add(image)
            }
        }
        allImagesMutableList.value = searchFilteredImages
    }

    fun removeSearchFilter() {
        searchFilteredImages.clear()
        allImagesMutableList.value = allImages
    }

}