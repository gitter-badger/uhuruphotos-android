package com.savvasdalkitsis.uhuruphotos.feed.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.savvasdalkitsis.uhuruphotos.albums.model.Album
import com.savvasdalkitsis.uhuruphotos.photos.model.Photo

@Composable
fun GridDateFeed(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    albums: List<Album>,
    showSelectionHeader: Boolean = false,
    columnCount: Int,
    gridState: LazyGridState = rememberLazyGridState(),
    onPhotoSelected: PhotoSelected,
    onPhotoLongPressed: (Photo) -> Unit,
    onAlbumSelectionClicked: (Album) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(columnCount),
        contentPadding = contentPadding,
    ) {
        albums.forEach { album ->
            item(album.id, span = { GridItemSpan(columnCount) }) {
                AlbumHeader(
                    modifier = Modifier.animateItemPlacement(),
                    album, showSelectionHeader) {
                    onAlbumSelectionClicked(album)
                }
            }
            for (photo in album.photos) {
                item(photo.thumbnailUrl.orEmpty()) {
                    PhotoThumbnail(
                        modifier = Modifier.animateItemPlacement(),
                        onPhotoSelected = onPhotoSelected,
                        photo = photo,
                        ratio = 1f,
                        contentScale = ContentScale.Crop,
                        onLongClick = onPhotoLongPressed,
                    )
                }
            }
        }
    }
}