package me.androidbox.beerpaging.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import me.androidbox.beerpaging.presentation.component.NewsItem
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme

@Composable
fun NewsScreen(
        newsPagingData: LazyPagingItems<ArticleModel>
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

    Box(
            modifier = Modifier.fillMaxSize()
    ) {
        if(newsPagingData.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
            )
        }
        else {
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
                    NewsItem(articleModel = articleModel)
                }

                item {
                    if(newsPagingData.loadState.append is LoadState.Loading) {
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

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    BeerPagingTheme {

    }
}