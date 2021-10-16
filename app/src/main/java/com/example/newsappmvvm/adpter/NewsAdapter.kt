package com.example.newsappmvvm.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.model.Article
import kotlinx.android.synthetic.main.article_item.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {



    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(differ.currentList[position])


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Article) = with(itemView) {
            itemView.apply {

                Glide.with(this).load(item.urlToImage).into(ivArticleImage)
                tvSource.text = item.source.name
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvPublishedAt.text = item.publishedAt
            }
            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}