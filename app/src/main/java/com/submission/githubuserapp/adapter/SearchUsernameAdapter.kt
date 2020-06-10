package com.submission.githubuserapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.githubuserapp.R
import com.submission.githubuserapp.activity.SearchDetailUserActivity
import com.submission.githubuserapp.model.SearchItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_search_user.view.*
import org.jetbrains.anko.startActivity

class SearchUsernameAdapter (private val context: Context?, var data: List<SearchItem?>?) :
    RecyclerView.Adapter<SearchUsernameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = data?.get(position)

        holder.tvUsername.text = searchResult?.login
        Glide.with(holder.itemView.context)
            .load(searchResult?.avatarUrl)
            .into(holder.imgUser)
        holder.cardIntent.setOnClickListener {
            context?.startActivity<SearchDetailUserActivity>("username" to searchResult?.login)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.tvSearchName
        var imgUser: CircleImageView = itemView.civSearchAvatar
        var cardIntent: ConstraintLayout = itemView.intentSearchDetail
    }
}