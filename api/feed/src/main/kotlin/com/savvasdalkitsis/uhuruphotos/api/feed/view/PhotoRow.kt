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
package com.savvasdalkitsis.uhuruphotos.api.feed.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.savvasdalkitsis.uhuruphotos.api.feed.view.PhotoRowSlot.EmptySlot
import com.savvasdalkitsis.uhuruphotos.api.feed.view.PhotoRowSlot.PhotoSlot
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaItem
import com.savvasdalkitsis.uhuruphotos.api.media.page.view.MediaItemSelected
import com.savvasdalkitsis.uhuruphotos.api.media.page.view.MediaItemThumbnail

@Composable
fun PhotoRow(
    modifier: Modifier = Modifier,
    maintainAspectRatio: Boolean = true,
    miniIcons: Boolean = false,
    onMediaItemSelected: MediaItemSelected,
    onPhotoLongPressed: (MediaItem) -> Unit,
    vararg slots: PhotoRowSlot
) {
    Row(modifier = modifier) {
        for (item in slots) {
            when (item) {
                is PhotoSlot -> {
                    val aspectRatio = when {
                        maintainAspectRatio -> item.mediaItem.ratio
                        else -> 1f
                    }
                    MediaItemThumbnail(
                        modifier = Modifier
                            .weight(aspectRatio),
                        mediaItem = item.mediaItem,
                        onItemSelected = onMediaItemSelected,
                        aspectRatio = aspectRatio,
                        contentScale = when {
                            maintainAspectRatio -> ContentScale.FillBounds
                            else -> ContentScale.Crop
                        },
                        miniIcons = miniIcons,
                        onLongClick = onPhotoLongPressed,
                    )
                }
                EmptySlot -> Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}