package me.androidbox.beerpaging.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.androidbox.beerpaging.R
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme
import kotlin.random.Random

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    articleModel: ArticleModel
) {

    var shouldShowProgress by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)) {

            Box(modifier = Modifier
                .weight(1F)
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))) {

                AsyncImage(
                    error = painterResource(id = R.drawable.back_soon),
                    modifier = Modifier.aspectRatio(2F / 5F, true),
                    onLoading = {
                        shouldShowProgress = true
                    },
                    onSuccess = {
                        shouldShowProgress = false
                    },
                    onError = {
                        shouldShowProgress = false
                    },
                    model = articleModel.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop)

                if (shouldShowProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center))
                }
            }

            Column(
                modifier = Modifier.weight(3F)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = articleModel.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = articleModel.description,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis)

                Spacer(modifier = Modifier.height(8.dp))

                Text(modifier = Modifier.fillMaxWidth(),
                    text = articleModel.author)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = articleModel.sourceName)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = articleModel.publishedAt,
                    textAlign = TextAlign.End)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItem() {
    BeerPagingTheme {
        NewsItem(articleModel = ArticleModel(
            id = Random.nextInt(),
            author = "Michelle Watson",
            title = "Maui wildfires: Nearly 400 remain unaccounted for in Maui after deadly wildfires, FBI-curated list shows -- a big decline from prior estimates - CNN",
            description = "Fulton County District Attorney Fani Willis will lay out the first details of her sprawling anti-racketeering case against former President Donald Trump, his White House chief of staff Mark Meadows and 17 other co-defendants at a federal court hearing on Monday",
            content = "Nearly 400 people are still listed as unaccounted for after this month’s devastating wildfires on Maui – a dramatic drop from the more than 1,000 previously believed missing but still a stark indicator of the disaster’s tragic impact.",
            urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230819165425-02-maui-devastation-081723.jpg?c=16x9&q=w_800,c_fill",
            publishedAt = "2023-08-25T10:32:00Z",
            url = "https://www.cnn.com/2023/08/25/us/maui-wildfires-unaccounted-for-list/index.html",
            sourceName = "CNN",
            sourceId = "CNN source Id"
        ))
    }
}