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
package com.savvasdalkitsis.uhuruphotos.settings.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.savvasdalkitsis.uhuruphotos.settings.view.state.SettingsState
import com.savvasdalkitsis.uhuruphotos.settings.viewmodel.SettingsAction
import com.savvasdalkitsis.uhuruphotos.settings.viewmodel.SettingsAction.ChangeImageDiskCache
import com.savvasdalkitsis.uhuruphotos.settings.viewmodel.SettingsAction.ClearImageDiskCache
import com.savvasdalkitsis.uhuruphotos.strings.R

@Composable
fun SettingsGroupImageDiskCache(
    state: SettingsState,
    action: (SettingsAction) -> Unit
) {
    SettingsGroupCache(
        title = stringResource(R.string.image_disk_cache),
        current = state.imageDiskCacheCurrent,
        initialMaxLimit = state.imageDiskCacheMax.toFloat(),
        clearAction = ClearImageDiskCache,
        changeCacheSizeAction = { ChangeImageDiskCache(it) },
        action = action
    )
}