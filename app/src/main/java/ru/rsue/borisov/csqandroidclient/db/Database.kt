package ru.rsue.borisov.csqandroidclient.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClientInf::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
}

