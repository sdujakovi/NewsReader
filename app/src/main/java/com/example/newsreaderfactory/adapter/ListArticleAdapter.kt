package com.example.newsreaderfactory.adapter

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsreaderfactory.MVP.model.Article
import com.example.newsreaderfactory.MVP.view.ListArticleFragment
import com.example.newsreaderfactory.R

class ListArticleAdapter(val articleList: List<Article>, val onClickListener: OnClickListener)
    : RecyclerView.Adapter<ListArticleAdapter.ViewHolder>(){

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val view_title = item.findViewById<TextView>(R.id.card_view_title)
        val view_story = item.findViewById<TextView>(R.id.card_view_story)
        val view_image = item.findViewById<ImageView>(R.id.card_view_image)

        fun bind(
            article: Article,
            onClickListener: OnClickListener
        ){
            val split = article.title.split(":")
            val shader =    LinearGradient(0f, 0f, 0f,view_story.textSize + 180f,
                            Color.GRAY, Color.WHITE , Shader.TileMode.CLAMP)
            view_story.paint.shader = shader

            view_title.text = split[0]
            view_story.text = article.description
            Glide.with(itemView.context).load(article.urlToImage).into(view_image)

            itemView.setOnClickListener{
                onClickListener.onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = articleList[position]
        holder.bind(data, onClickListener)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
}