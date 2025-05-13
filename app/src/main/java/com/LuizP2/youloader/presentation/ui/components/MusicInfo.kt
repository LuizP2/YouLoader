package com.LuizP2.youloader.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.LuizP2.youloader.R
import com.LuizP2.youloader.data.model.Music
import com.LuizP2.youloader.data.model.Thumbnail
import com.LuizP2.youloader.data.model.VideoImageType
import com.LuizP2.youloader.data.model.VideoItem
import com.LuizP2.youloader.data.model.VideoSnippet
import com.LuizP2.youloader.data.model.videoId
import com.LuizP2.youloader.presentation.viewmodel.DownloadViewModel
import com.LuizP2.youloader.presentation.viewmodel.downloadMp3
import com.LuizP2.youloader.resource.Resource

@Composable
fun MusicInfo(
    modifier: Modifier = Modifier,
    item: VideoItem? = null,
    viewmodel: DownloadViewModel
) {

    val musicState = viewmodel.music
    val context = LocalContext.current

    LaunchedEffect(musicState) {
        if (musicState is Resource.Success) {
            val music = (musicState as Resource.Success<Music>).data
            val url = music.url
            val title = music.title

            downloadMp3(
                context = context,
                fileUrl = url,
                fileName = "$title.mp3"
            )


            viewmodel.FinishDownloadProcess()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item?.snippet?.thumbnails?.high?.url
                        ?: "https://img.freepik.com/premium-vector/music-sketch-logo-doodle-icon-isolated-dark-background_159242-373.jpg",
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        item?.snippet?.title ?: "Title",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Text(
                        item?.snippet?.description ?: "Description",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    if (item != null) {
                        viewmodel.Download(id = item.id.videoId, title = item.snippet.title)
                    }
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.download_icon),
                    contentDescription = "Download Icon"
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
    when (musicState) {
        is Resource.DownloadFinished -> Text("Download concluído! Verifique sua pasta de downloads.")
        is Resource.Loading -> Text("Processando...")
        is Resource.Error -> Text("Erro: ${(musicState as Resource.Error).exception.message}")
        is Resource.Success -> Text("Iniciando download…")
    }
}


@Preview(name = "MusicInfo", showBackground = true)
@Composable
private fun PreviewMusicInfo() {
    MusicInfo(item = null, viewmodel = viewModel<DownloadViewModel>())
}