package ru.rsue.borisov.csqandroidclient.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientInf(
    @PrimaryKey val id: Int,
    val token: String
)


