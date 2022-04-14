package com.savvasdalkitsis.librephotos.search.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.savvasdalkitsis.librephotos.feed.view.Feed
import com.savvasdalkitsis.librephotos.feed.view.state.FeedState
import com.savvasdalkitsis.librephotos.infrastructure.extensions.copy
import com.savvasdalkitsis.librephotos.search.mvflow.SearchAction
import com.savvasdalkitsis.librephotos.search.mvflow.SearchAction.*
import com.savvasdalkitsis.librephotos.search.view.state.SearchResults
import com.savvasdalkitsis.librephotos.search.view.state.SearchState
import com.savvasdalkitsis.librephotos.ui.view.FullProgressBar

@ExperimentalComposeUiApi
@Composable fun Search(
    state: SearchState,
    action: (SearchAction) -> Unit,
    controllersProvider: com.savvasdalkitsis.librephotos.app.navigation.ControllersProvider,
    contentPadding: PaddingValues,
) {
    Column {
        Spacer(modifier = Modifier.height(contentPadding.calculateTopPadding()))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .onFocusChanged { focusState ->
                    action(ChangeFocus(focusState.isFocused))
                }
                .focusRequester(controllersProvider.focusRequester!!),
            maxLines = 1,
            singleLine = true,
            trailingIcon = {
                AnimatedVisibility(
                    visible = state.showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { action(ClearSearch) }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { action(SearchFor(state.query)) }
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "searchIcon"
                )
            },
            label = { Text("Search for something") },
            value = state.query,
            onValueChange = {
                action(ChangeQuery(it))
            }
        )
        when (state.searchResults) {
            SearchResults.Idle -> {}
            SearchResults.Searching -> FullProgressBar()
            is SearchResults.Found -> Feed(
                contentPadding = contentPadding.copy(top = 0.dp),
                state = FeedState(isLoading = false, state.searchResults.albums),
            )
        }
    }
}