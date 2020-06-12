package com.heixss.exchange.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(@PrimaryKey val currency: String)