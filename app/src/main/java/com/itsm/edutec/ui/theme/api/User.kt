package com.itsm.edutec.ui.theme.api

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("profile_picture")
    val profilePicture: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("isActive")
    val isActive: Boolean
)