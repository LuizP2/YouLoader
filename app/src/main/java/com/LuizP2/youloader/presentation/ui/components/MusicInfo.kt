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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.LuizP2.youloader.R
import com.LuizP2.youloader.data.model.Thumbnail
import com.LuizP2.youloader.data.model.VideoImageType
import com.LuizP2.youloader.data.model.VideoItem
import com.LuizP2.youloader.data.model.VideoSnippet
import com.LuizP2.youloader.data.model.videoId
import com.LuizP2.youloader.presentation.viewmodel.DownloadViewModel


val videoBase = VideoItem(
    id = videoId("ekzHIouo8Q4"),
    snippet = VideoSnippet(
        title = "Bruno Mars - When I Was Your Man (Official Music Video)",
        description = "1.469.099.275 visualizações  5 de fev. de 2013  #UnorthodoxJukebox #BrunoMars #WhenIWasYourMan\n" +
                "The official music video for Bruno Mars' \"When I Was Your Man\" from the album 'Unorthodox Jukebox'. \n" +
                "\n" +
                "Directed by Cameron Duddy & Bruno Mars\n" +
                "\n" +
                "\uD83D\uDD14 Subscribe for the latest official music videos, live performances, lyric videos, official audio, and more ➤ https://Atlantic.lnk.to/BMsubscribe\n" +
                "\n" +
                "Watch All Of Bruno Mars’ Official Music Videos ➤ https://bit.ly/2U7I3mi\n" +
                "\n" +
                "See Bruno Mars on tour ➤ Visit http://brunomars.com/tour for dates and more info.\n" +
                "\n" +
                "Get Bruno Mars merchandise ➤ https://brunom.rs/brunomarsstore\n" +
                "\n" +
                "Follow Bruno Mars:\n" +
                "http://www.brunomars.com\n" +
                "  / brunomars  \n" +
                "  / brunomars  \n" +
                "  / brunomars  \n" +
                "\n" +
                "The official YouTube channel of Atlantic Records artist Bruno Mars. \n" +
                " \n" +
                "15x GRAMMY Award winner and 27x GRAMMY Award nominee Bruno Mars is a celebrated singer, songwriter, producer, and musician with iconic hits like \"The Lazy Song\", \"That's What I Like\", \"Just The Way You Are\", \"24K Magic\", \"Locked Out Of Heaven\", and \"When I Was Your Man\". His legendary body of work also includes blockbuster albums such as Doo-Wops & Hooligans, Unorthodox Jukebox, and 24K Magic, as well as era-defining collaborations like \"Uptown Funk\" with Mark Ronson, \"Finesse\" with Cardi B, and \"Nothin' On You\" with B.o.B. Forever classic, yet supremely innovative, Bruno continues to redefine music, style, and popular culture, pushing the boundaries of pop, R&B, funk, soul, hip-hop, and dance, and remains as influential as ever.\n" +
                "\n" +
                "#BrunoMars #WhenIWasYourMan #UnorthodoxJukebox #OfficialMusicVideo #AtlanticRecords",
        thumbnails = VideoImageType(
            high = Thumbnail(url = "https://i.ytimg.com/vi/ekzHIouo8Q4/hqdefault.jpg")
        ),
        publishedAt = "22/02/2023"
    )
)

@Composable
fun MusicInfo(
    modifier: Modifier = Modifier,
    item: VideoItem? = null,
    viewmodel: DownloadViewModel
) {

    var musicDownloaded = viewmodel.music
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
            Row(modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item?.snippet?.thumbnails?.high?.url?:"https://img.freepik.com/premium-vector/music-sketch-logo-doodle-icon-isolated-dark-background_159242-373.jpg",
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(item?.snippet?.title?:"Title", style = MaterialTheme.typography.bodyLarge, overflow = TextOverflow.Ellipsis, maxLines = 2)
                    Text(item?.snippet?.description?:"Description", style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Ellipsis, maxLines = 2)
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
    Text(
        text = "the music: ${musicDownloaded}",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 8.dp)
    )
}


@Preview(name = "MusicInfo", showBackground = true)
@Composable
private fun PreviewMusicInfo() {
    MusicInfo(item = videoBase, viewmodel = viewModel<DownloadViewModel>())
}