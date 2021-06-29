package com.example.newsreaderfactory.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.service.API

@Database(entities = arrayOf(Article::class), version = 3)
abstract class MyDatabase : RoomDatabase() {

    abstract fun articleDao() : DAO

    companion object {
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()

        @Synchronized
        internal fun getInstance(context: Context): MyDatabase =
            instance ?: synchronized(this){
                instance ?: createDatabase(context).also{
                    instance = it
                }
            }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "article.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}
