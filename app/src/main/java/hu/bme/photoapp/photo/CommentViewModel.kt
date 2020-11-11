package hu.bme.photoapp.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.photoapp.home.HomeRepository

class CommentViewModel : ViewModel() {

    private val repository: HomeRepository = HomeRepository()

    val allComments: MutableLiveData<MutableList<CommentContainer>> = MutableLiveData<MutableList<CommentContainer>>()

    fun getAllComments(id: String) {
        allComments.value?.clear()
        repository.getAllComments(id, this::addComments, this::showError)
    }

    fun postComment(id: String, text: String, onSuccess :() -> Unit) {
        repository.postComment(id, text, onSuccess, this::showError)
    }

    private fun addComments(commentContainer: CommentContainer) {
        allComments.postValue(mutableListOf(commentContainer))
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}