package me.androidbox.beerpaging.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.component.NewsDetailItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsDetailScreen(
    modifier: Modifier,
    listOfArticle: List<ArticleModel>
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        listOfArticle.count()
    }

    if(listOfArticle.isNotEmpty()) {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            key = { id ->
                id
            }
        ) { index ->
            NewsDetailItem(
                modifier = Modifier.fillMaxSize(),
                articleModel = listOfArticle[index]
            )
        }
    }
    else {
        Box(modifier = modifier) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "There are no articles"
            )
        }
    }
}