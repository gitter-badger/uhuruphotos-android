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

import androidx.lifecycle.ViewModel
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumAction
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumEffect
import com.savvasdalkitsis.uhuruphotos.autoalbum.mvflow.AutoAlbumMutation
import com.savvasdalkitsis.uhuruphotos.autoalbum.view.state.AutoAlbumState
import com.savvasdalkitsis.uhuruphotos.viewmodel.ActionReceiver
import com.savvasdalkitsis.uhuruphotos.viewmodel.ActionReceiverHost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoAlbumViewModel @Inject constructor(
    autoAlbumHandler: AutoAlbumHandler,
    autoAlbumReducer: AutoAlbumReducer,
) : ViewModel(),
    ActionReceiverHost<AutoAlbumState, AutoAlbumEffect, AutoAlbumAction, AutoAlbumMutation> {

    override val actionReceiver = ActionReceiver(
        autoAlbumHandler,
        autoAlbumReducer,
        AutoAlbumState(),
    )
}