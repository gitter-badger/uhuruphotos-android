/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.api.media.remote.domain.usecase

import androidx.work.WorkInfo
import com.savvasdalkitsis.uhuruphotos.api.db.domain.model.media.DbRemoteMediaItemDetails
import com.savvasdalkitsis.uhuruphotos.api.db.domain.model.media.DbRemoteMediaItemSummary
import kotlinx.coroutines.flow.Flow

interface RemoteMediaUseCase {

    fun String?.toRemoteUrl(): String?

    fun String?.toThumbnailUrlFromIdNullable(): String?

    fun String.toThumbnailUrlFromId(isVideo: Boolean = false): String

    fun String?.toFullSizeUrlFromIdNullable(isVideo: Boolean = false): String?

    fun String.toFullSizeUrlFromId(isVideo: Boolean = false): String

    fun observeAllPhotoDetails(): Flow<List<DbRemoteMediaItemDetails>>

    fun observeFavouriteRemoteMedia(): Flow<Result<List<DbRemoteMediaItemSummary>>>

    suspend fun getRemoteMediaItemDetails(id: String): DbRemoteMediaItemDetails?

    fun observeHiddenRemoteMedia(): Flow<List<DbRemoteMediaItemSummary>>

    suspend fun getFavouriteMediaSummaries(): Result<List<DbRemoteMediaItemSummary>>

    suspend fun getFavouriteMediaSummariesCount(): Result<Long>

    suspend fun getHiddenMediaSummaries(): List<DbRemoteMediaItemSummary>

    suspend fun setMediaItemFavourite(id: String, favourite: Boolean): Result<Unit>

    suspend fun refreshDetailsNowIfMissing(id: String): Result<Unit>

    suspend fun refreshDetailsNow(id: String): Result<Unit>

    suspend fun refreshFavouriteMedia()

    suspend fun refreshHiddenMedia()

    fun trashMediaItem(id: String)

    fun deleteMediaItem(id: String)

    fun restoreMediaItem(id: String)

    fun downloadOriginal(id: String, video: Boolean)

    fun observeOriginalFileDownloadStatus(id: String): Flow<WorkInfo.State>
}