package com.example.videofeed.model
import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("data")
    val results: List<UserData>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("support")
    val support: Support?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)

data class Support(
    @SerializedName("text")
    val text: String?,
    @SerializedName("url")
    val url: String?
)
