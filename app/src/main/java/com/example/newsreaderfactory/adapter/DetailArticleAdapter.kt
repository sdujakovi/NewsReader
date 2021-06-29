package com.example.newsreaderfactory.adapter

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailArticleAdapter(val articleList: List<Article>)
    :RecyclerView.Adapter<DetailArticleAdapter.DetailViewHolder>(){

    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view_title = itemView.findViewById<TextView>(R.id.title_view_pager)
        val view_story = itemView.findViewById<TextView>(R.id.story_view_pager)
        val view_image = itemView.findViewById<ImageView>(R.id.image_view_pager)

        fun bind(
            article: Article
        ) {
            view_title.text = article.title
            view_story.text = article.description
            Glide.with(itemView.context).load(article.urlToImage).into(view_image)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailArticleAdapter.DetailViewHolder {
        return DetailArticleAdapter.DetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false))
    }

    override fun onBindViewHolder(holder: DetailArticleAdapter.DetailViewHolder, position: Int) {
        val data = articleList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }


}