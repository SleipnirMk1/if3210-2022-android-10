package com.example.perludilindungi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("count_total")
    val countTotal: Int?,

    @SerializedName("results")
    val results: ArrayList<NewsResultResponse>
) : Parcelable

@Parcelize
data class NewsResultResponse(
    @SerializedName("title")
    val title: String?,

    @SerializedName("link")
    val link: List<String>?,

    @SerializedName("guid")
    val guid: String?,

    @SerializedName("pubDate")
    val pubDate: String?,

    @SerializedName("description")
    val desc: NewsDescriptionResponse?,

    @SerializedName("enclosure")
    val encl: NewsEnclosureResponse
): Parcelable

@Parcelize
data class NewsDescriptionResponse(
    @SerializedName("__cdata")
    val cdata: String?
): Parcelable

@Parcelize
data class NewsEnclosureResponse(
    @SerializedName("_url")
    val imageUrl: String?,

    @SerializedName("_type")
    val imageType: String?
): Parcelable
