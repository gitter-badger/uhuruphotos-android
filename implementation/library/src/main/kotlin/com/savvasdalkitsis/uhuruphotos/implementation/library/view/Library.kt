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
package com.savvasdalkitsis.uhuruphotos.implementation.library.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.savvasdalkitsis.uhuruphotos.api.feed.view.state.FeedDisplay
import com.savvasdalkitsis.uhuruphotos.api.home.view.HomeScaffold
import com.savvasdalkitsis.uhuruphotos.api.ui.view.FullProgressBar
import com.savvasdalkitsis.uhuruphotos.implementation.library.seam.LibraryAction
import com.savvasdalkitsis.uhuruphotos.implementation.library.seam.LibraryAction.Refresh
import com.savvasdalkitsis.uhuruphotos.implementation.library.view.state.LibraryState

@Composable
internal fun Library(
    state: LibraryState,
    homeFeedDisplay: FeedDisplay,
    action: (LibraryAction) -> Unit,
    navHostController: NavHostController,
) {
    HomeScaffold(
        modifier = Modifier,
        showLibrary = true,
        navController = navHostController,
        userInformationState = null,
        homeFeedDisplay = homeFeedDisplay,
    ) { contentPadding ->
        when {
            state.loading
                    && state.autoAlbums == null
                    && state.userAlbums == null -> FullProgressBar()
            else -> SwipeRefresh(
                indicatorPadding = contentPadding,
                state = rememberSwipeRefreshState(isRefreshing = state.loading),
                onRefresh = { action(Refresh) }
            ) {
                LibraryGrid(contentPadding, state, action)
            }
        }
    }
}