package me.androidbox.beerpaging.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.screen.NewsDetailScreen
import me.androidbox.beerpaging.presentation.screen.NewsItemEvent
import me.androidbox.beerpaging.presentation.screen.NewsItemState
import me.androidbox.beerpaging.presentation.screen.NewsScreen
import me.androidbox.beerpaging.presentation.screen.listOfNavigationItem
import me.androidbox.beerpaging.presentation.theme.BusbyNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusbyNewsApp(
    newsPagingData: LazyPagingItems<ArticleModel>,
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onNewsLinkedClicked: (newsLink: String) -> Unit,
    newsItemEvent: (NewsItemEvent) -> Unit,
    newsItemState: NewsItemState,
    selectedTheme: Boolean,
    onSelectedTheme: (selectedTheme: AppTheme) -> Unit) {

    var selectedNavItem by rememberSaveable() {
        mutableIntStateOf(0)
    }
    /*
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
        }*/

    /*
        BusbyNewsTheme(
            darkTheme = useDarkColors
        ) {
            // A surface container using the 'background' color from the theme

            var newsLinkState by remember {
                mutableStateOf("")
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
    */

    val scrollBehavior = enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Busby News", style = MaterialTheme.typography.titleLarge) },
                scrollBehavior = topAppBarScrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(imageVector =  Icons.Default.ExitToApp, contentDescription = null)
                    }
                },
                actions = {
                    Switch(
                        modifier = Modifier.padding(end = 16.dp),
                        checked = newsItemState.isDarkThemeSelected,
                        onCheckedChange = { isChecked ->
                            if(isChecked) {
                                onSelectedTheme(AppTheme.MODE_NIGHT)
                            }
                            else {
                                onSelectedTheme(AppTheme.MODE_DAY)
                            }
                            newsItemEvent(NewsItemEvent.OnDarkThemeSelected(isDarkTheme = isChecked))
                        },
                        thumbContent = {
                            Icon(
                                imageVector = if (newsItemState.isDarkThemeSelected) {
                                    Icons.Default.Check
                                } else {
                                    Icons.Default.Close
                                },
                                contentDescription = null
                            )
                        })
                })
        },
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

        if(newsPagingData.itemCount > 0) {
            NewsDetailScreen(
                modifier = Modifier
                    .padding(paddingValues),
                listOfArticle = newsPagingData.itemSnapshotList.items
            )
        }
/*
        NewsScreen(
            newsPagingData = newsPagingData,
            modifier = Modifier
                .padding(paddingValues),
            topAppBarScrollBehavior = scrollBehavior,
            onNewsLinkedClicked = { newsLink ->
                //      newsLinkState = newsLink
            },
            newsItemState = newsItemState,
            newsItemEvent = { newsItemEvent ->
                //      newsViewModel.onNewsItemEvent(newsItemEvent)
            },
            selectedTheme = true, // useDarkColors,
            onSelectedTheme = { appTheme ->
                //    newsApplication.toggleDarkThemeOff(appTheme)
            }
        )
*/
    }
}


@Composable
@Preview
fun PreviewBusbyNewsApp() {
    BusbyNewsTheme {
     //   BusbyNewsApp()
    }
}