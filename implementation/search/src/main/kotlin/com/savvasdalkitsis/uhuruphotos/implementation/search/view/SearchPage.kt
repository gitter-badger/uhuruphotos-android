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
package com.savvasdalkitsis.uhuruphotos.implementation.search.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savvasdalkitsis.uhuruphotos.api.compose.blurIf
import com.savvasdalkitsis.uhuruphotos.api.feed.view.FeedDisplayActionButton
import com.savvasdalkitsis.uhuruphotos.api.home.view.HomeScaffold
import com.savvasdalkitsis.uhuruphotos.api.strings.R.string
import com.savvasdalkitsis.uhuruphotos.api.ui.view.Logo
import com.savvasdalkitsis.uhuruphotos.implementation.search.seam.SearchAction
import com.savvasdalkitsis.uhuruphotos.implementation.search.seam.SearchAction.ChangeDisplay
import com.savvasdalkitsis.uhuruphotos.implementation.search.view.state.SearchResults.Found
import com.savvasdalkitsis.uhuruphotos.implementation.search.view.state.SearchState

@Composable
fun SearchPage(
    state: SearchState,
    isShowingPopUp: Boolean,
    action: (SearchAction) -> Unit,
    actionBarContent: @Composable () -> Unit,
    navHostController: NavHostController,
    additionalContent: @Composable () -> Unit,
) {
    HomeScaffold(
        modifier = Modifier
            .blurIf(isShowingPopUp)
            .imeNestedScroll(),
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Logo()
                Text(stringResource(string.search))
            }
        },
        navController = navHostController,
        homeFeedDisplay = state.feedDisplay,
        showLibrary = state.showLibrary,
        actionBarContent = {
            AnimatedVisibility(state.searchResults is Found) {
                FeedDisplayActionButton(
                    onChange = { action(ChangeDisplay(it)) },
                    currentFeedDisplay = state.searchDisplay
                )
            }
            actionBarContent()
        }
    ) { contentPadding ->
        Search(
            state = state,
            action = action,
            contentPadding = contentPadding
        )
        additionalContent()
    }
}