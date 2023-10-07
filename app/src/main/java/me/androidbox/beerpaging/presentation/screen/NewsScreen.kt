package me.androidbox.beerpaging.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.AppTheme
import me.androidbox.beerpaging.presentation.component.NewsItem
import me.androidbox.beerpaging.presentation.theme.BusbyNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
        newsPagingData: LazyPagingItems<ArticleModel>,
        modifier: Modifier = Modifier,
        topAppBarScrollBehavior: TopAppBarScrollBehavior,
        onNewsLinkedClicked: (newsLink: String) -> Unit,
        newsItemEvent: (NewsItemEvent) -> Unit,
        newsItemState: NewsItemState,
        selectedTheme: Boolean,
        onSelectedTheme: (selectedTheme: AppTheme) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = newsPagingData.loadState) {
        if(newsPagingData.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                    context,
                    "Error Loading ${(newsPagingData.loadState.refresh as LoadState.Error).error.message}",
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        NewsItemItems(
            paddingValues = paddingValues,
            newsPagingData = newsPagingData,
            onNewsLinkedClicked = onNewsLinkedClicked,
            newsItemEvent = newsItemEvent,
            newsItemState = newsItemState
        )
    }
}

@Composable
fun NewsItemItems(
    paddingValues: PaddingValues,
    newsPagingData: LazyPagingItems<ArticleModel>,
    onNewsLinkedClicked: (newsLink: String) -> Unit,
    newsItemEvent: (NewsItemEvent) -> Unit,
    newsItemState: NewsItemState

) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        if (newsPagingData.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    key = { articleModel ->
                        articleModel.id
                    },
                    items = newsPagingData.itemSnapshotList.items
                ) { articleModel ->
                    NewsItem(
                        articleModel = articleModel,
                        onNewsLinkClicked = onNewsLinkedClicked,
                        newsItemEvent = newsItemEvent,
                        newsItemState = newsItemState,
                        titleTextLayoutResult = ::getTextLayoutResult
                    )
                }

                /* Item to show progress when scrolling and is fetching */
                item {
                    if (newsPagingData.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

private fun getTextLayoutResult(hasVisualOverflow: Boolean, lineCount: Int): Boolean {
    return hasVisualOverflow || lineCount > 1
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    BusbyNewsTheme {

    }
}