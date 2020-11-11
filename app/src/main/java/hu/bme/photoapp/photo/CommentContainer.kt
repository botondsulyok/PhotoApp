package hu.bme.photoapp.photo

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.model.User

@JsonClass(generateAdapter = true)
class CommentContainer(
    val _id: String = "",
    val comments: List<Comment>
)