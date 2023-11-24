package com.sport.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Bola::class,Raket::class,USER::class], version = 8 )
abstract class SPORT : RoomDatabase() {
    abstract fun daooo() : ALLDAO
    companion object {
        @Volatile
        private var instance : SPORT ?=null
        private var LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also { instance }
        }
        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,SPORT::class.java,"olahraga"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}