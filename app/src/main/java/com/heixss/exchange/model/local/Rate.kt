package com.heixss.exchange.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rate(
    @PrimaryKey val currency: String,
    val value: String,
    val base: String
)