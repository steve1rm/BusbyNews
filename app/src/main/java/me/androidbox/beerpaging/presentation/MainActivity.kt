package me.androidbox.beerpaging.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.androidbox.beerpaging.presentation.screen.NewsScreen
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme
import me.androidbox.beerpaging.presentation.viewmodel.NewsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val newsViewModel: NewsViewModel = hiltViewModel()
            val newsHeadLines = newsViewModel.newsPager.collectAsLazyPagingItems()

            BeerPagingTheme {
                // A surface container using the 'background' color from the theme
                val scrollBehavior = enterAlwaysScrollBehavior()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsScreen(
                        newsPagingData = newsHeadLines,
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
                        topAppBarScrollBehavior = scrollBehavior
                    )
                }
            }
        }

        lifecycleScope.launch {
            val config = lifecycleScope.async {
                getConfigFromApi()
            }

            val news = lifecycleScope.async {
                getNewsFromApi(config.await())
            }

            val user = lifecycleScope.async {
                getUserFromApi()
            }

            println("${user.await().id} ${news.await().id}")
        }
    }
}

data class Config(val id: Int)
data class News(val id: Int)
data class User(val id: Int)

suspend fun getConfigFromApi(): Config {
    println("Getting config from API")
    Thread.sleep(1000)

    return Config(1)
}

suspend fun getNewsFromApi(config: Config): News {
    println("Getting News from API")
    Thread.sleep(1000)

    return News(2)
}

suspend fun getUserFromApi(): User {
    println("Getting User from API")
    Thread.sleep(1000)

    return User(3)
}