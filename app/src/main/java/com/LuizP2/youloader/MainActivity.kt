package com.LuizP2.youloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.LuizP2.youloader.presentation.viewmodel.SearchViewByIdViewModel
import com.LuizP2.youloader.presentation.ui.screens.DownloadVideoScreen
import com.LuizP2.youloader.presentation.ui.screens.SearchScreen
import com.LuizP2.youloader.presentation.ui.theme.YouLoaderTheme
import com.LuizP2.youloader.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YouLoaderTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars)
                ) { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val idviewModel: SearchViewByIdViewModel = hiltViewModel()
    val viewModel: SearchViewModel = hiltViewModel()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        DownloadVideoScreen(viewModel = idviewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YouLoaderTheme {
        App()
    }
}