package com.example.newsreaderfactory.MVP.view

import com.example.newsreaderfactory.MVP.model.Article

interface ArticleInterface {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showResult(result: Any)

}