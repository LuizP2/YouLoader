package com.LuizP2.youloader.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.LuizP2.youloader.presentation.viewmodel.SearchViewModel
import com.LuizP2.youloader.resource.Resource

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val videosState = viewModel.videos

    Column(modifier = Modifier.padding(16.dp)) {
        var query by remember { mutableStateOf("") }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar vídeos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.search(query) }) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (videosState) {
            is Resource.Loading -> CircularProgressIndicator()
            is Resource.Success -> LazyColumn {
                Log.i("SearchScreen", "Videos: ${videosState.data}")
                items(videosState.data.size) { index ->
                    val video = videosState.data[index]
                    Text(
                        text = video.snippet.title,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is Resource.Error -> Text("Erro: ${videosState.exception.message}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevSearch() {
    SearchScreen(viewModel = hiltViewModel())
}