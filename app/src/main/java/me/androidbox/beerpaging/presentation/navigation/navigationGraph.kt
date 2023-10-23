package me.androidbox.beerpaging.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import me.androidbox.beerpaging.presentation.screen.NewsDetailScreen
import me.androidbox.beerpaging.presentation.screen.NewsScreen
import me.androidbox.beerpaging.presentation.viewmodel.NewsViewModel

@Composable
fun navigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val newsViewModel: NewsViewModel = hiltViewModel()
    val newsHeadLines = newsViewModel.newsPager.collectAsLazyPagingItems()
    val newsItemState by newsViewModel.newsItemState.collectAsStateWithLifecycle()

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screens.NewsScreen.route) {
            NewsScreen(
                modifier = Modifier.fillMaxSize(),
                newsItemState = newsItemState,
                newsPagingData = newsHeadLines,
                onNewsLinkedClicked = { newsLink ->
                    println(newsLink)
                },
                newsItemEvent = { newsItemEvent ->
                    println(newsItemEvent)
                },
            )
        }

        composable(route = Screens.NewsDetailScreen.route) {
            NewsDetailScreen(
                modifier = Modifier.fillMaxSize(),
                listOfArticle = newsHeadLines.itemSnapshotList.items
            )
        }

        composable(route = Screens.WebNewsScreen.route) {
            println("WebNewsScreen")
            // WebNewsScreen()
        }
    }
}