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
package com.savvasdalkitsis.uhuruphotos.feedpage.module

import com.savvasdalkitsis.uhuruphotos.feedpage.navigation.FeedPageNavigationTarget
import com.savvasdalkitsis.uhuruphotos.feedpage.usecase.FeedPageUseCase
import com.savvasdalkitsis.uhuruphotos.api.navigation.NavigationTarget
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FeedBindingsModule {

    @Binds
    @IntoSet
    abstract fun navigationTarget(navigationTarget: FeedPageNavigationTarget): NavigationTarget

    @Binds
    abstract fun feedPageUseCase(feedPageUseCase: FeedPageUseCase):
            com.savvasdalkitsis.uhuruphotos.api.feedpage.usecase.FeedPageUseCase
}