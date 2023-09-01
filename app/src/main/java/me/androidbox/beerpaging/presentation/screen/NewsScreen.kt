package me.androidbox.beerpaging.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.component.NewsItem
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme

@Composable
fun NewsScreen(
    pagingData: LazyPagingItems<ArticleModel>
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = pagingData.loadState) {
        if(pagingData.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error Loading ${(pagingData.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            key = { articleModel ->
                  articleModel.id
            },
            items = pagingData.itemSnapshotList.items
        ) { articleModel ->
            NewsItem(articleModel = articleModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    BeerPagingTheme {

    }
}