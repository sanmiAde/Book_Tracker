package com.sanmiaderibigbe.booktracker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VolumeInfo {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("authors")
    @Expose
    var authors: List<String>? = null
    @SerializedName("pageCount")
    @Expose
    var pageCount: Int? = null
    @SerializedName("categories")
    @Expose
    var categories: List<String>? = null
    @SerializedName("averageRating")
    @Expose
    var averageRating: Double? = null


}
