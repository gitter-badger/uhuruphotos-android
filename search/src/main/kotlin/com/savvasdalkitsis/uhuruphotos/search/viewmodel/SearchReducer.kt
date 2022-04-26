package com.savvasdalkitsis.uhuruphotos.search.viewmodel

import com.savvasdalkitsis.uhuruphotos.search.mvflow.SearchMutation
import com.savvasdalkitsis.uhuruphotos.search.view.state.SearchResults
import com.savvasdalkitsis.uhuruphotos.search.view.state.SearchState
import com.savvasdalkitsis.uhuruphotos.viewmodel.Reducer

fun searchReducer() : Reducer<SearchState, SearchMutation> = { state, mutation ->
    when (mutation) {
        is SearchMutation.QueryChanged -> state.copy(query = mutation.query)
        is SearchMutation.FocusChanged -> state.copy(showClearButton = mutation.focused)
        SearchMutation.SearchCleared -> state.copy(query = "")
        SearchMutation.SearchStarted -> state.copy(searchResults = SearchResults.Searching)
        is SearchMutation.SearchResultsUpdated -> state.copy(
            searchResults = when {
                mutation.albums.isEmpty() -> SearchResults.Searching
                else -> SearchResults.Found(mutation.albums)
            }
        )
        is SearchMutation.UserBadgeStateChanged -> state.copy(userInformationState = mutation.userInformationState)
        SearchMutation.HideAccountOverview -> state.copy(showAccountOverview = false)
        SearchMutation.ShowAccountOverview -> state.copy(showAccountOverview = true)
        is SearchMutation.ChangeDisplay -> state.copy(feedDisplay = mutation.feedDisplay)
        SearchMutation.HideLogOutConfirmation -> state.copy(showLogOutConfirmation = false)
        SearchMutation.ShowLogOutConfirmation -> state.copy(showLogOutConfirmation = true)
    }
}