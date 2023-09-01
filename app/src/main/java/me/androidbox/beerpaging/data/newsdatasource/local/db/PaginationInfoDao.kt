package me.androidbox.beerpaging.data.newsdatasource.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import me.androidbox.beerpaging.data.newsdatasource.local.entity.PaginationInfo

@Dao
interface PaginationInfoDao {
    @Query("SELECT * FROM PaginationInfo WHERE id = 1")
    suspend fun getPaginationInfo(): PaginationInfo?

    @Upsert
    suspend fun upsertPaginationInfo(paginationInfo: PaginationInfo)

    @Query("DELETE FROM PaginationInfo")
    suspend fun clearAll()
}