package hu.bme.photoapp.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.photoapp.home.HomeRepository

class CommentViewModel : ViewModel() {

    private val repository: HomeRepository

    val allComments: MutableLiveData<MutableList<Comment>> = MutableLiveData<MutableList<Comment>>()

    init {
        repository = HomeRepository()
    }

    fun getAllComments() {
        allComments.value?.clear()
        repository.getAllComments(this::addComments, this::showError)
    }

    private fun addComments(comments: List<Comment>) {
        allComments.postValue(comments.toMutableList())
    }

    private fun showError(t: Throwable) {
        t.printStackTrace()
    }

}