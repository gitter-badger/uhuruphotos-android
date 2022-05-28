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
package com.savvasdalkitsis.uhuruphotos.autoalbum.viewmodel

import com.savvasdalkitsis.uhuruphotos.albums.api.model.Album
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction.LoadAlbum
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction.NavigateBack
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction.PersonSelected
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction.SelectedPhoto
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction.SwipeToRefresh
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumEffect
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumEffect.NavigateToPerson
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumEffect.OpenPhotoDetails
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumMutation
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumMutation.ErrorLoading
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumMutation.Loading
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumMutation.ShowAutoAlbum
import com.savvasdalkitsis.uhuruphotos.autoalbum.usecase.AutoAlbumsUseCase
import com.savvasdalkitsis.uhuruphotos.autoalbum.view.state.AutoAlbum
import com.savvasdalkitsis.uhuruphotos.autoalbum.view.state.AutoAlbumState
import com.savvasdalkitsis.uhuruphotos.db.albums.GetAutoAlbum
import com.savvasdalkitsis.uhuruphotos.db.albums.GetPeopleForAutoAlbum
import com.savvasdalkitsis.uhuruphotos.infrastructure.date.DateDisplayer
import com.savvasdalkitsis.uhuruphotos.infrastructure.extensions.safelyOnStartIgnoring
import com.savvasdalkitsis.uhuruphotos.api.log.log
import com.savvasdalkitsis.uhuruphotos.people.api.view.state.toPerson
import com.savvasdalkitsis.uhuruphotos.photos.api.model.Photo
import com.savvasdalkitsis.uhuruphotos.photos.usecase.PhotosUseCase
import com.savvasdalkitsis.uhuruphotos.viewmodel.Handler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.io.IOException
import javax.inject.Inject

class AutoAlbumHandler @Inject constructor(
    private val autoAlbumsUseCase: AutoAlbumsUseCase,
    private val photosUseCase: PhotosUseCase,
    private val dateDisplayer: DateDisplayer,
) : Handler<AutoAlbumState, AutoAlbumEffect, AutoAlbumAction, AutoAlbumMutation> {

    private val loading = MutableSharedFlow<AutoAlbumMutation>()
    private var albumId: Int? = null

    override fun invoke(
        state: AutoAlbumState,
        action: AutoAlbumAction,
        effect: suspend (AutoAlbumEffect) -> Unit,
    ): Flow<AutoAlbumMutation> = when (action) {
        is LoadAlbum -> merge(
            autoAlbumsUseCase.observeAutoAlbum(action.albumId)
                .map { it.toAutoAlbum() }
                .map(::ShowAutoAlbum),
            loading
        ).safelyOnStartIgnoring {
            albumId = action.albumId
            refreshAlbum()
        }
        SwipeToRefresh -> flow {
            refreshAlbum()
        }
        is SelectedPhoto -> flow {
            effect(
                with(action) {
                    OpenPhotoDetails(photo.id, center, scale, photo.isVideo)
                }
            )
        }
        NavigateBack -> flow {
            effect(AutoAlbumEffect.NavigateBack)
        }
        is PersonSelected -> flow {
            effect(NavigateToPerson(action.person.id))
        }
    }

    private suspend fun refreshAlbum() {
        loading.emit(Loading(true))
        try {
            autoAlbumsUseCase.refreshAutoAlbum(albumId!!)
        } catch (e: IOException) {
            log(e)
            loading.emit(ErrorLoading)
        } finally {
            loading.emit(Loading(false))
        }
    }

    private suspend fun Pair<List<GetAutoAlbum>, List<GetPeopleForAutoAlbum>>.toAutoAlbum(): AutoAlbum =
        let { (photoEntries, people) ->
            AutoAlbum(
                title = photoEntries.firstOrNull()?.title ?: "",
                people = with(photosUseCase) {
                    people.map { person ->
                        person.toPerson { it.toAbsoluteUrl() }
                    }
                },
                albums = photoEntries.groupBy { entry ->
                    dateDisplayer.dateString(entry.timestamp)
                }.entries.map { (date, photos) ->
                    Album(
                        id = date,
                        date = date,
                        location = null,
                        photos = photos.map {
                            Photo(
                                id = it.photoId.toString(),
                                thumbnailUrl = with(photosUseCase) {
                                    it.photoId.toThumbnailUrlFromId()
                                },
                                isFavourite = it.isFavorite ?: false,
                                isVideo = it.video ?: false,
                            )
                        }
                    )
                }
            )
        }
}
