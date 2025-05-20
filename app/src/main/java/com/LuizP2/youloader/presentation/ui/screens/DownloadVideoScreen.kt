package com.LuizP2.youloader.presentation.ui.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.LuizP2.youloader.presentation.ui.components.MusicInfo
import com.LuizP2.youloader.presentation.viewmodel.DownloadViewModel
import com.LuizP2.youloader.presentation.viewmodel.SearchViewByIdViewModel
import com.LuizP2.youloader.resource.Resource
import com.LuizP2.youloader.util.DownloadMp3Utils.getVideoIdFromURL

@Composable
fun DownloadVideoScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewByIdViewModel = hiltViewModel(),
    onNavigateToSearchScreen: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Tudo ok", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "aceita a internet ai", Toast.LENGTH_SHORT).show()
        }
    }
    var enableMusicInfo by remember { mutableStateOf(false) }
    val video = viewModel.video
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToSearchScreen) {
                Icon(Icons.Default.Search, contentDescription = "pesquisar videos")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("URL") },
                        placeholder = { Text("Enter the youtube video URL here") },
                        maxLines = 1,
                        singleLine = true,
                        supportingText = { Text(text = "Enter the URL of the video you want to download") },
                        )
                    ElevatedButton(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            permissionLauncher.launch(Manifest.permission.INTERNET)
                            val videoId = text
                            if (videoId.isNotEmpty()) {
                                viewModel.searchById(getVideoIdFromURL(videoId))
                                enableMusicInfo = true

                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter a valid URL",
                                    Toast.LENGTH_SHORT
                                )
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
                                val downloadModel: DownloadViewModel = hiltViewModel<DownloadViewModel>()
                                MusicInfo(
                                    item = (video).data,
                                    viewmodel = hiltViewModel<DownloadViewModel>(),
                                    onDownloadClick = { id ->
                                        downloadModel.download(id)
                                    },
                                )
                            }

                            is Resource.Error -> {
                                // Handle error
                                Text(text = "Error: ${(video).exception.message}")
                            }

                            else -> {/*Shows nothing*/
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(name = "DownloadForm", showBackground = true)
@Composable
private fun PreviewDownloadForm() {
    DownloadVideoScreen(onNavigateToSearchScreen = {})
}