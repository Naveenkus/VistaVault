package com.example.vistavault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vistavault.util.Constants.REMOTE_KEYS_TABLE

@Entity(tableName = REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
