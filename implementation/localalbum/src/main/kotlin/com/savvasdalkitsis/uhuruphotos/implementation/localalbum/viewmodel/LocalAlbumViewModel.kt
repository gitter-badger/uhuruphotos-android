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
package com.savvasdalkitsis.uhuruphotos.implementation.localalbum.viewmodel

import androidx.lifecycle.ViewModel
import com.savvasdalkitsis.uhuruphotos.api.albumpage.seam.AlbumPageAction
import com.savvasdalkitsis.uhuruphotos.api.albumpage.seam.AlbumPageEffect
import com.savvasdalkitsis.uhuruphotos.api.albumpage.seam.AlbumPageMutation
import com.savvasdalkitsis.uhuruphotos.api.albumpage.view.state.AlbumPageState
import com.savvasdalkitsis.uhuruphotos.api.feed.view.state.FeedState
import com.savvasdalkitsis.uhuruphotos.api.seam.CompositeActionHandler
import com.savvasdalkitsis.uhuruphotos.api.seam.Either
import com.savvasdalkitsis.uhuruphotos.api.seam.Mutation
import com.savvasdalkitsis.uhuruphotos.api.seam.Seam
import com.savvasdalkitsis.uhuruphotos.api.seam.SeamViaHandler.Companion.handler
import com.savvasdalkitsis.uhuruphotos.implementation.localalbum.seam.LocalAlbumAction
import com.savvasdalkitsis.uhuruphotos.implementation.localalbum.seam.LocalAlbumActionHandler
import com.savvasdalkitsis.uhuruphotos.implementation.localalbum.seam.LocalAlbumEffect
import com.savvasdalkitsis.uhuruphotos.implementation.localalbum.seam.LocalAlbumPageActionHandler
import com.savvasdalkitsis.uhuruphotos.implementation.localalbum.view.state.LocalAlbumState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LocalAlbumViewModel @Inject constructor(
    localAlbumActionHandler: LocalAlbumActionHandler,
    localAlbumPageActionHandler: LocalAlbumPageActionHandler,
) : ViewModel(), Seam<
        Pair<AlbumPageState, LocalAlbumState>,
        Either<AlbumPageEffect, LocalAlbumEffect>,
        Either<AlbumPageAction, LocalAlbumAction>,
        Mutation<Pair<AlbumPageState, LocalAlbumState>>> by handler(
    CompositeActionHandler(
        localAlbumPageActionHandler,
        localAlbumActionHandler,
    ),
    AlbumPageState() to LocalAlbumState(),
)