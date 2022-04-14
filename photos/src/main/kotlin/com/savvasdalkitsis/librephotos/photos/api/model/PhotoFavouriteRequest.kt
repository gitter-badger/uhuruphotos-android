package com.savvasdalkitsis.librephotos.photos.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoFavouriteRequest(
    @field:Json(name = "image_hashes") val imageHashes: List<String>,
    @field:Json(name = "favorite") val favourite: Boolean
)