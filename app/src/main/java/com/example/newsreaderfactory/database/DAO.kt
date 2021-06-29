package com.example.newsreaderfactory.database

import androidx.room.*
import com.example.newsreaderfactory.MVP.model.Article
import io.reactivex.rxjava3.core.Completable

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    @Delete
    fun deleteArticle(article: Article): Int

    @Query("SELECT * from Article")
    fun selectAllArticle(): MutableList<Article>

    @Query("DELETE FROM Article")
    fun deleteAllArticle()
}