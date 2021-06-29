package com.example.newsreaderfactory.MVP.presenter

import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.model.ArticleList
import com.example.newsreaderfactory.MVP.view.ArticleInterface
import com.example.newsreaderfactory.service.API
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ArticlePresenter(val view: ArticleInterface) {

    fun getData(){
        val data : ArrayList<Article> = arrayListOf()
        view.showLoading()

        API.createAPI().getNews("bbc-news", "top", "6946d0c07a1c4555a4186bfcade76398")
            .enqueue(object : Callback, retrofit2.Callback<ArticleList>{
                override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                    view.hideLoading()
                    if (response.isSuccessful){
                        if(response.body()!!.articles.size == 0){
                            view.showError("Podaci nedostupni!")
                        }else{
                            for (item in response.body()!!.articles){
                                data.add(item)
                            }
                            view.showResult(data)
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
    }
}
