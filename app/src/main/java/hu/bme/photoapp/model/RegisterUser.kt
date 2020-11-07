package hu.bme.photoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RegisterUser(val email: String, val password: String)