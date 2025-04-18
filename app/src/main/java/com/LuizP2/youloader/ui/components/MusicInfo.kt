package com.LuizP2.youloader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.LuizP2.youloader.R

@Composable
fun MusicInfo(
    modifier: Modifier = Modifier
) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://img.freepik.com/premium-vector/music-sketch-logo-doodle-icon-isolated-dark-background_159242-373.jpg",
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Music Title", style = MaterialTheme.typography.bodyLarge)
                    Text("file size: 10MB", style = MaterialTheme.typography.bodySmall)
                }
            }

            IconButton(
                modifier = Modifier
                    .size(48.dp),
                onClick = { /* TODO */ }
            ) {
                Image(
                    painter = painterResource(R.drawable.download_icon),
                    contentDescription = "Download Icon"
                )
            }
        }
    }
}


@Preview(name = "MusicInfo", showBackground = true)
@Composable
private fun PreviewMusicInfo() {
    MusicInfo()
}