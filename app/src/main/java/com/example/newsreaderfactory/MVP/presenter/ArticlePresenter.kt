package com.example.newsreaderfactory.MVP.presenter

import android.content.Context
import android.util.Log
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.model.ArticleList
import com.example.newsreaderfactory.MVP.view.ArticleInterface
import com.example.newsreaderfactory.database.MyDatabase
import com.example.newsreaderfactory.service.API
import io.reactivex.rxjava3.core.Completable
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.security.auth.callback.Callback

class ArticlePresenter(val view: ArticleInterface, context: Context) {

    val database: MyDatabase = MyDatabase.getInstance(context)

    fun getData(){
        val data : ArrayList<Article> = arrayListOf()


        if(database.articleDao().selectAllArticle().isEmpty()){
            view.showLoading()
            API.createAPI().getNews("bbc-news", "top", "6946d0c07a1c4555a4186bfcade76398")
                .enqueue(object : Callback, retrofit2.Callback<ArticleList>{
                    override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                        view.hideLoading()
                        if (response.isSuccessful){
                            if(response.body()!!.articles.size == 0){
                                view.showError("Podaci nedostupni!")
                            }else{

                                /*For some reason the autoincrement property does not work
                                  while fetching the data and caching it localy, there for
                                  here is an manual incrementing variable*/
                                var i: Int = 1
                                for (item in response.body()!!.articles){
                                    var artit: Article
                                    if(item.publishedAt == null){
                                        artit = Article (
                                                i, item.author, item.description, "datum", item.title, item.url, item.urlToImage
                                                )
                                        data.add(artit)
                                        database.articleDao().insertArticle(artit)
                                        }
                                    i++
                                    }
                                view.showResult(database.articleDao().selectAllArticle())
                            }
                        }else{
                            view.showError("Neuspješno povezivanje s poslužiteljem!")
                        }
                    }
                    override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                        view.hideLoading()
                        view.showError("Veza istekla!")
                    }
                })
        }else{
            view.showLoading()
            view.showResult(database.articleDao().selectAllArticle())
            view.hideLoading()
        }
    }
}
