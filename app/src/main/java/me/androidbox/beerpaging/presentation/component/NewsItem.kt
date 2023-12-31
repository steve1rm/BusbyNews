package me.androidbox.beerpaging.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.androidbox.beerpaging.R
import me.androidbox.beerpaging.domain.ArticleModel
import me.androidbox.beerpaging.presentation.screen.NewsItemEvent
import me.androidbox.beerpaging.presentation.screen.NewsItemState
import me.androidbox.beerpaging.presentation.theme.BeerPagingTheme
import java.time.ZonedDateTime
import kotlin.random.Random

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    articleModel: ArticleModel,
    onNewsLinkClicked: (newsLink: String) -> Unit,
    newsItemEvent: (NewsItemEvent) -> Unit,
    newsItemState: NewsItemState,
    titleTextLayoutResult: (hasVisualOverflow: Boolean, lineCount: Int) -> Boolean
) {

    var showMoreTitleClicked by remember {
        mutableStateOf(false)
    }

    var shouldShowMoreTitle by remember {
        mutableStateOf(false)
    }

    var showMoreDescriptionClicked by remember {
        mutableStateOf(false)
    }

    var shouldShowMoreDescription by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = MaterialTheme.shapes.medium
    ) {

        Box(
            modifier = Modifier.wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        error = painterResource(id = R.drawable.back_soon),
                        modifier = Modifier
                            .aspectRatio(2F / 5F, false)
                            .fillMaxHeight(),
                        onLoading = {
                            newsItemEvent(NewsItemEvent.OnProgressUpdated(shouldShowProgress = true))
                        },
                        onSuccess = {
                            newsItemEvent(NewsItemEvent.OnProgressUpdated(shouldShowProgress = false))
                        },
                        onError = {
                            newsItemEvent(NewsItemEvent.OnProgressUpdated(shouldShowProgress = false))
                        },
                        model = articleModel.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                if (newsItemState.shouldShowProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center))
                }
            }

            Column(modifier = Modifier
                .weight(3F)
                .fillMaxHeight())
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                        color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    text = articleModel.title,
                    maxLines =  if (showMoreTitleClicked) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        shouldShowMoreTitle =  titleTextLayoutResult(textLayoutResult.hasVisualOverflow, textLayoutResult.lineCount)
/*
                        newsItemEvent(NewsItemEvent.OnShowMoreTitleClicked(shouldShowMoreTitle))

                        newsItemEvent(NewsItemEvent.OnShowMoreTitleTextClicked(
                            newsItemState.showMoreOrLessTitleText.copy(
                                hasTextOverflow = textLayoutResult.hasVisualOverflow,
                                lineCount = textLayoutResult.lineCount
                            )
                        ))*/
                    })

                if (shouldShowMoreTitle) {
                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            showMoreTitleClicked = !showMoreTitleClicked
/*
                            newsItemEvent(NewsItemEvent.OnShowMoreTitleClicked(showMoreTitleClicked))
                            newsItemEvent(NewsItemEvent.OnShowMoreTitleTextClicked())
*/
                        }) {
                        Text(
                            text = if (showMoreTitleClicked) "Show less" else "Show more",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        text = articleModel.description,
                        maxLines = if (showMoreDescriptionClicked) Int.MAX_VALUE else 5,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            shouldShowMoreDescription =
                                textLayoutResult.hasVisualOverflow || textLayoutResult.lineCount > 5
                        })

                    if (shouldShowMoreDescription) {
                        TextButton(
                            modifier = Modifier.align(Alignment.End),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            onClick = {
                                showMoreDescriptionClicked = !showMoreDescriptionClicked
                            }) {
                            Text(
                                text = if (showMoreDescriptionClicked) "Show less" else "Show more",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    if (articleModel.author.isUrl()) {
                        ClickableText(
                            style = MaterialTheme.typography.labelLarge,
                            text = AnnotatedString(
                                text = articleModel.author,
                                spanStyle = SpanStyle(
                                    color = Color.Blue,
                                    textDecoration = TextDecoration.Underline
                                )
                            ),
                            onClick = {
                                onNewsLinkClicked(articleModel.author)
                            })
                    } else {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.labelLarge,
                            text = articleModel.author
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        text = articleModel.sourceName
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd).padding(bottom = 4.dp, end = 8.dp),
                text = ZonedDateTime.parse(articleModel.publishedAt).toLocalDate()
                    .toString(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

fun String.isUrl(): Boolean {
    return this.matches(Regex("(http|https)://[\\w\\-_.]+\\.[a-z]{2,6}"))
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
        ),
            onNewsLinkClicked = ::print,
            newsItemEvent = {},
            newsItemState = NewsItemState(),
            titleTextLayoutResult = { _, _, -> true}
        )
    }
}