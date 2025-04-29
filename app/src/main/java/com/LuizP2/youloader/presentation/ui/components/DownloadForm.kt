package com.LuizP2.youloader.presentation.ui.components

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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


@Composable
fun DownloadForm(
    modifier: Modifier = Modifier
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
                    if (text.isNotEmpty() && text.isNotBlank()) {
                        enableMusicInfo = true
                    } else {
                        Toast.makeText(context, "Please enter a valid URL", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                enabled = text.isNotEmpty() && text.isNotBlank()
            ) { Text("submit") }
            if (enableMusicInfo) {
                MusicInfo()
            }
        }
    }
}

@Preview(name = "DownloadForm", showBackground = true)
@Composable
private fun PreviewDownloadForm() {
    DownloadForm()
}