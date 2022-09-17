package com.example.phonebook.service

import androidx.room.*
import com.example.phonebook.model.Kisiler

@Dao
interface KisilerDao {
    @Query("SELECT *  FROM kisiler")
    suspend fun tumkisiler(): List<Kisiler>

    @Insert
    suspend fun kisiEkle(kisiler: Kisiler)

    @Update
    suspend fun kisiGuncelle(kisiler: Kisiler)

    @Delete
    suspend fun kisiSil(kisiler: Kisiler)
}