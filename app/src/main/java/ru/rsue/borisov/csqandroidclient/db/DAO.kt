package ru.rsue.borisov.csqandroidclient.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ClientDao {

   /* @Query("SELECT id FROM UserInf")
    fun getUserId(): UserInf

    @Query("SELECT token FROM UserInf")
    fun getUserToken(): UserInf*/


    @Insert
    fun insertClientIdAndToken(vararg client: ClientInf)


    /*  Используем возвращаемое значение типа Flow в описании метода,
    и Room генерирует весь необходимый код для обновления Flow при обновлении базы данных.*/
    @Query("SELECT * FROM ClientInf ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<ClientInf>>
}

