package com.example.newsreaderfactory.MVP.model

import com.google.gson.annotations.SerializedName

data class ArticleList(
    @SerializedName("articles")
    val articles: ArrayList<Article>,
    @SerializedName("sortBy")
    val sortBy: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("status")
    val status: String
)