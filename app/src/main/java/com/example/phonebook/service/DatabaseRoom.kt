package com.example.phonebook.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.phonebook.model.Kisiler

@Database(entities = [Kisiler::class], version = 1)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun getKisilerDao(): KisilerDao

    companion object {
        //Erisim İçin Alınan Nesne
        var INSTANCE: DatabaseRoom? = null
        fun DatabaseAccess(context: Context): DatabaseRoom? { //bu  DatabaseRoom sınıfından bir nesne getiricek

            if (INSTANCE == null) { //eğer null ise erişim yapacak
                synchronized(DatabaseRoom::class) { //synchronized metodunu DatabaseRoom sınıfında uygulayacak
                    INSTANCE = Room.databaseBuilder(  //burda INSTANCE'ye yetki verilecek
                        context.applicationContext,
                        DatabaseRoom::class.java,
                        "rehber.sqlite"
                    )
                        .createFromAsset("rehber.sqlite").build()
                }
            }
            return INSTANCE
        }
    }

}