package com.example.newsreaderfactory.MVP.presenter

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.health.TimerStat
import android.util.Log
import android.widget.Toast
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.model.ArticleList
import com.example.newsreaderfactory.MVP.view.ArticleInterface
import com.example.newsreaderfactory.database.MyDatabase
import com.example.newsreaderfactory.service.API
import com.example.newsreaderfactory.util.Constants.Companion.API_KEY
import com.example.newsreaderfactory.util.Constants.Companion.SORT
import com.example.newsreaderfactory.util.Constants.Companion.SOURCE
import io.reactivex.rxjava3.core.Completable
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.lang.System.currentTimeMillis
import java.sql.Timestamp
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class ArticlePresenter(val view: ArticleInterface, context: Context) {

    val database: MyDatabase = MyDatabase.getInstance(context)
    val context:Context = context
    private lateinit var sharedPreferencese: SharedPreferences

    /***
     * Funckija iz preferenci korisnika dohvaća zadnje osvježavanje baze podataka.
     * Ukoliko je baza podataka prazna ili je sadržaj stariji od 5 min, izvodi se
     * request prema poslužitelju za podatke, u suprotnome uzimaju se podaci iz baze podataka.
     */
    fun getData(){
        val data : ArrayList<Article> = arrayListOf()

        if(database.articleDao().selectAllArticle().isEmpty() || getTimeDiff(currentTimeMillis()) >= 5){
            view.showLoading()

            API.createAPI().getNews(SOURCE, SORT, API_KEY)
                .enqueue(object : Callback, retrofit2.Callback<ArticleList>{
                    override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                        view.hideLoading()

                        if (response.isSuccessful){
                            if(response.body()!!.articles.size == 0){
                                view.showError("Podaci nedostupni!")

                            }else{
                                /***
                                 * TODO: Iz nekog razloga autoinkrement rooma neće.
                                 *       Zasad riješeno sa forom i pojedinačnim unosom
                                 *       Trebalo bi slike pohranit na uređaju, trenutno se uvijek povlače
                                 */
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

                                sharedPreferencese = context.getSharedPreferences("time", Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPreferencese.edit()
                                editor.putLong("time", currentTimeMillis()).apply()
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

    /***
     * Funkcija prima kao parametar trenutno vrijeme kako bi izračunala razliku
     * između zadnjeg dohvaćanja podataka. Podatke dohvaća iz sharedPreferenca
     */
    fun getTimeDiff(currentTime: Long): Long{
        sharedPreferencese = context.getSharedPreferences("time", Context.MODE_PRIVATE)
        val lastUpdate = sharedPreferencese.getLong("time", 0)
        return (currentTime - lastUpdate)/60000
    }
}
