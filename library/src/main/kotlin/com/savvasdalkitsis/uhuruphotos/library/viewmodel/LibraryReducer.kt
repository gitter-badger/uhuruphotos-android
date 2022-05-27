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
package com.savvasdalkitsis.uhuruphotos.library.viewmodel

import com.savvasdalkitsis.uhuruphotos.library.mvflow.LibraryMutation
import com.savvasdalkitsis.uhuruphotos.library.mvflow.LibraryMutation.*
import com.savvasdalkitsis.uhuruphotos.library.view.state.LibraryState
import com.savvasdalkitsis.uhuruphotos.viewmodel.Reducer

fun libraryReducer() : Reducer<LibraryState, LibraryMutation> = { state, mutation ->
    when (mutation) {
        is DisplayAutoAlbums -> state.copy(autoAlbums = mutation.albums,)
        is ShowAutoAlbumSorting -> state.copy(sorting = mutation.sorting)
        is Loading -> state.copy(isLoading = mutation.loading)
    }
}