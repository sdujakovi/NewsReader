package com.example.newsreaderfactory.MVP.view

interface ArticleInterface {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showResult(result: Any)
}