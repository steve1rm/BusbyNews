package me.androidbox.beerpaging.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import me.androidbox.beerpaging.R
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.theme.BusbyNewsTheme
import kotlin.random.Random

@Composable
fun NewsDetailItem(
    modifier: Modifier = Modifier,
    articleModel: ArticleModel
) {
    Column(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            model = articleModel.urlToImage,
            error = { painterResource(id = R.drawable.back_soon) },
            loading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            contentDescription = "News item picture",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = articleModel.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = articleModel.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewNewsDetailItem() {
    BusbyNewsTheme {
        NewsDetailItem(
            modifier = Modifier.fillMaxWidth(),
            articleModel = ArticleModel(
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