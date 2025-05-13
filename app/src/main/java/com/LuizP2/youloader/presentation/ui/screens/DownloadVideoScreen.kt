package com.LuizP2.youloader.presentation.ui.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LuizP2.youloader.data.model.VideoItem
import com.LuizP2.youloader.presentation.ui.components.MusicInfo
import com.LuizP2.youloader.presentation.viewmodel.DownloadViewModel
import com.LuizP2.youloader.presentation.viewmodel.SearchViewByIdViewModel
import com.LuizP2.youloader.resource.Resource

@Composable
fun DownloadVideoScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewByIdViewModel = hiltViewModel()
) {

    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Tudo ok", Toast.LENGTH_SHORT).show()
            // Proceed with the download logic
        } else {
            Toast.makeText(context, "aceita a internet ai", Toast.LENGTH_SHORT).show()
        }
    }
    var enableMusicInfo by remember { mutableStateOf(false) }
    val video = viewModel.video

    Box(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("URL") },
                placeholder = { Text("Enter the URL") },
                maxLines = 1,
                singleLine = true,
                supportingText = { Text(text = "Enter the URL of the video you want to download") },
            )
            ElevatedButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    permissionLauncher.launch(Manifest.permission.INTERNET)
                    val videoId = text.substringAfter("v=", "").substringBefore("&")
                    if (videoId.isNotEmpty()) {
                        viewModel.searchById(videoId)
                        enableMusicInfo = true

                    } else {
                        Toast.makeText(context, "Please enter a valid URL", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                enabled = text.isNotEmpty() && text.isNotBlank()
            ) { Text("submit") }
            if (enableMusicInfo) {
                when (video) {
                    is Resource.Loading -> {
                        // Show loading indicator
                        CircularProgressIndicator()
                    }

                    is Resource.Success -> {
                        // Show the music info
                        MusicInfo(
                            item = (video as Resource.Success<VideoItem>).data,
                            viewmodel = viewModel<DownloadViewModel>()
                        )
                    }

                    is Resource.Error -> {
                        // Handle error
                        Text(text = "Error: ${(video as Resource.Error).exception.message}")
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview(name = "DownloadForm", showBackground = true)
@Composable
private fun PreviewDownloadForm() {
    DownloadVideoScreen()
}