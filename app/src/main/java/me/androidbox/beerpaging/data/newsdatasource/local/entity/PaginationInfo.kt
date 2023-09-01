package me.androidbox.beerpaging.data.newsdatasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaginationInfo(
    @PrimaryKey
    val id: Int = 1,
    val nextPage: Int
)
