package com.LuizP2.youloader.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.LuizP2.youloader.presentation.ui.components.MusicInfo
import com.LuizP2.youloader.presentation.viewmodel.DownloadViewModel
import com.LuizP2.youloader.presentation.viewmodel.SearchViewModel
import com.LuizP2.youloader.resource.Resource
import androidx.hilt.navigation.compose.hiltViewModel as hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel, onBack: () -> Unit) {
    val videosState = searchViewModel.videos
    var listIsVisible: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Download por URL") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var query by remember { mutableStateOf("") }

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar vídeos") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                searchViewModel.search(query)
                listIsVisible = true}) {
                Text("Buscar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            val downloadViewModel: DownloadViewModel = hiltViewModel<DownloadViewModel>()
            if (listIsVisible) {
                when (videosState) {
                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Success -> LazyColumn {
                        items(videosState.data.size) { index ->
                            val video = videosState.data[index]
                            MusicInfo(
                                item = video,
                                viewmodel = downloadViewModel,
                                onDownloadClick = { id ->
                                    downloadViewModel.download(id)
                                },
                            )
                        }
                    }
                    is Resource.Error -> Text("Erro: ${videosState.exception.message}")
                    else -> {}
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PrevSearch() {
    SearchScreen(searchViewModel = hiltViewModel(), onBack = { })
}
