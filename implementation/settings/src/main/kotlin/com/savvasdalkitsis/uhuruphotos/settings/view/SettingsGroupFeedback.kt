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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.savvasdalkitsis.uhuruphotos.settings.seam.SettingsAction
import com.savvasdalkitsis.uhuruphotos.settings.seam.SettingsAction.ClearLogFileClicked
import com.savvasdalkitsis.uhuruphotos.settings.seam.SettingsAction.SendFeedbackClicked
import com.savvasdalkitsis.uhuruphotos.strings.R
import com.savvasdalkitsis.uhuruphotos.api.icons.R as Icons

@Composable
internal fun SettingsGroupFeedback(
    action: (SettingsAction) -> Unit,
    collapsed: MutableState<Boolean> = remember { mutableStateOf(false) },
) {
    SettingsGroup(
        title = stringResource(R.string.feedback),
        collapsed = collapsed,
    ) {
        SettingsOutlineButtonRow(
            buttonText = stringResource(R.string.send_feedback_with_logs),
            icon = Icons.drawable.ic_feedback,
        ) {
            action(SendFeedbackClicked)
        }
        SettingsButtonRow(buttonText = stringResource(R.string.clear_log_file)) {
            action(ClearLogFileClicked)
        }
    }
}