package me.androidbox.beerpaging.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.mapper.toArticleModel
import me.androidbox.beerpaging.presentation.screen.NewsItemEvent
import me.androidbox.beerpaging.presentation.screen.NewsItemState
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    pager: Pager<Int, ArticleEntity>
) : ViewModel() {

    val newsPager = pager
        .flow
        .map { pagingData ->
            pagingData.map { articleEntity ->
                articleEntity.toArticleModel()
            }
        }
        .cachedIn(viewModelScope)

    private val _newsItemState = MutableStateFlow<NewsItemState>(NewsItemState())
    val newsItemState = _newsItemState.asStateFlow()

    fun onNewsItemEvent(newsItemEvent: NewsItemEvent) {
        when(newsItemEvent) {
            is NewsItemEvent.OnProgressUpdated -> {
                _newsItemState.update { newsItemState ->
                    newsItemState.copy(
                        shouldShowProgress = newsItemEvent.shouldShow
                    )
                }
            }
            is NewsItemEvent.OnShowMoreTitleClicked -> {

            }
        }
    }
}