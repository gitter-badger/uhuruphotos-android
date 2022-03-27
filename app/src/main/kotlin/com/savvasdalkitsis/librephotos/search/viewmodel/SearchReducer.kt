package com.savvasdalkitsis.librephotos.search.viewmodel

import com.savvasdalkitsis.librephotos.search.mvflow.SearchMutation
import com.savvasdalkitsis.librephotos.search.view.SearchState
import net.pedroloureiro.mvflow.Reducer

class SearchReducer : Reducer<SearchState, SearchMutation> {

    override fun invoke(
        state: SearchState,
        mutation: SearchMutation,
    ): SearchState = when (mutation) {
        is SearchMutation.QueryChanged -> state.copy(query = mutation.query)
        is SearchMutation.FocusChanged -> state.copy(showClearButton = mutation.focused)
        SearchMutation.SearchCleared -> state.copy(query = "")
    }

}
