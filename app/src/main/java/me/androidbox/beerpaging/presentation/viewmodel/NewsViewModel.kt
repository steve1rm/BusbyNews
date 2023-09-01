package me.androidbox.beerpaging.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.androidbox.beerpaging.data.newsdatasource.local.entity.ArticleEntity
import me.androidbox.beerpaging.data.newsdatasource.mapper.toArticleModel
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
}