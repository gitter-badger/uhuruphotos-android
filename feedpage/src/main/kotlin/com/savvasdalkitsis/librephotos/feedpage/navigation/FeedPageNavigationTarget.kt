package com.savvasdalkitsis.librephotos.feedpage.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.annotation.ExperimentalCoilApi
import com.savvasdalkitsis.librephotos.feedpage.view.FeedPage
import com.savvasdalkitsis.librephotos.feedpage.mvflow.FeedPageAction
import com.savvasdalkitsis.librephotos.feedpage.mvflow.FeedPageEffect
import com.savvasdalkitsis.librephotos.feedpage.view.state.FeedPageState
import com.savvasdalkitsis.librephotos.feedpage.viewmodel.FeedPageEffectsHandler
import com.savvasdalkitsis.librephotos.feedpage.viewmodel.FeedPageViewModel
import com.savvasdalkitsis.librephotos.home.module.HomeModule.HomeNavigationTargetSearch
import com.savvasdalkitsis.librephotos.app.navigation.ControllersProvider
import com.savvasdalkitsis.librephotos.app.navigation.navigationTarget
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class FeedPageNavigationTarget @Inject constructor(
    private val controllersProvider: ControllersProvider,
    private val feedPageEffectsHandler: FeedPageEffectsHandler,
    @HomeNavigationTargetSearch private val searchNavigationName: String,
) {

    @ExperimentalCoroutinesApi
    @ExperimentalCoilApi
    @FlowPreview
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    fun NavGraphBuilder.create() {
        navigationTarget<FeedPageState, FeedPageEffect, FeedPageAction, FeedPageViewModel>(
            name = name,
            effects = feedPageEffectsHandler,
            initializer = { _, actions -> actions(FeedPageAction.LoadFeed) },
            createModel = { hiltViewModel() }
        ) { state, actions ->
            FeedPage(controllersProvider, state, name, searchNavigationName, actions)
        }
    }

    companion object {
        const val name = "feed"
    }
}