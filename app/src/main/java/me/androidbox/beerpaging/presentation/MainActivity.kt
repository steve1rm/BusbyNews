package me.androidbox.beerpaging.presentation

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout.LayoutParams
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.beerpaging.presentation.screen.BottomNavigationItem
import me.androidbox.beerpaging.presentation.screen.NewsScreen
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme
import me.androidbox.beerpaging.presentation.viewmodel.NewsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var newsApplication: NewsApplication

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listOfNavigationItem = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home,
                hasInfo = false),

            BottomNavigationItem(
                title = "News",
                selectedIcon = Icons.Filled.Info,
                unSelectedIcon = Icons.Outlined.Info,
                hasInfo = false,
                infoCount = 45),

            BottomNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unSelectedIcon = Icons.Outlined.Settings,
                hasInfo = true))

        setContent {
            val newsViewModel: NewsViewModel = hiltViewModel()
            val newsHeadLines = newsViewModel.newsPager.collectAsLazyPagingItems()
            val newsItemState by newsViewModel.newsItemState.collectAsState()
            val theme = newsApplication.appTheme.collectAsState()

            var selectedNavItem by rememberSaveable() {
                mutableIntStateOf(0)
            }

            val useDarkColors = when(theme.value) {
                AppTheme.MODE_DAY -> {
                    false
                }
                AppTheme.MODE_NIGHT -> {
                    true
                }
                AppTheme.MODE_AUTO -> {
                    isSystemInDarkTheme()
                }
            }

            BeerPagingTheme(
                darkTheme = useDarkColors
            ) {
                // A surface container using the 'background' color from the theme
                val scrollBehavior = enterAlwaysScrollBehavior()
                var newsLinkState by remember {
                    mutableStateOf("")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                listOfNavigationItem.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedNavItem == index,
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if(item.infoCount != null) {
                                                        Badge {
                                                            Text(text = item.infoCount.toString())
                                                        }
                                                    }
                                                    else {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (selectedNavItem == index) {
                                                        item.selectedIcon
                                                    } else {
                                                        item.unSelectedIcon
                                                    },
                                                    contentDescription = item.title
                                                )
                                            }
                                        },
                                        onClick = {
                                            selectedNavItem = index
                                        },
                                        label = {
                                            Text(text = item.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        if (newsLinkState.isBlank()) {
                            NewsScreen(
                                newsPagingData = newsHeadLines,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
                                topAppBarScrollBehavior = scrollBehavior,
                                onNewsLinkedClicked = { newsLink ->
                                    newsLinkState = newsLink
                                },
                                newsItemState = newsItemState,
                                newsItemEvent = { newsItemEvent ->
                                    newsViewModel.onNewsItemEvent(newsItemEvent)
                                },
                                selectedTheme = useDarkColors,
                                onSelectedTheme = { appTheme ->
                                    newsApplication.toggleDarkThemeOff(appTheme)
                                }
                            )
                        } else {
                            WebViewScreen(webLink = newsLinkState)
                            newsLinkState = ""
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(webLink: String) {
    AndroidView(
        factory = { context ->

            WebView(context).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT)

                loadUrl(webLink)
            } },
        update = { webView ->
            webView.loadUrl(webLink)
        },
        modifier = Modifier.fillMaxSize())
}

data class Config(val id: Int)
data class News(val id: Int)
data class User(val id: Int)


/*lifecycleScope.launch {
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
}*/
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