package com.LuizP2.youloader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.LuizP2.youloader.R

@Composable
fun MusicInfo(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.CenterEnd,

    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            AsyncImage(
                model = "https://img.freepik.com/premium-vector/music-sketch-logo-doodle-icon-isolated-dark-background_159242-373.jpg"?: painterResource(R.drawable.music_placholder),
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Music Title")
                Text("file size: 10MB")
            }
        }
            IconButton(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                onClick = { /* TODO: Handle click */ },

            ) {
                Image(
                    painter = painterResource(R.drawable.download_icon),
                    contentDescription = "Download Icon",
                    modifier = Modifier
                        .padding(end = 20.dp)

                )
            }
    }
}

@Preview(name = "MusicInfo", showBackground = true)
@Composable
private fun PreviewMusicInfo() {
    MusicInfo()
}