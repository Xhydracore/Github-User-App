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
import com.submission.githubuserapp.model.Favorite
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_search_user.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import org.jetbrains.anko.startActivity

class FavoriteUserAdapter (private val context: Context) :
    RecyclerView.Adapter<FavoriteUserAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    fun addItem(favorite: Favorite) {
        this.listFavorite.add(favorite)
        notifyItemInserted(this.listFavorite.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorite.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val fav = listFavorite[position]

        holder.tvUsername.text = fav.login
        Glide.with(holder.itemView.context)
            .load(fav.avatar_url)
            .into(holder.imgAvatar)
        holder.itemFav.setOnClickListener {
            context.startActivity<SearchDetailUserActivity>("username" to fav.login)
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemFav: ConstraintLayout = itemView.intentSearchDetail
        var tvUsername: TextView = itemView.tvSearchName
        var imgAvatar: CircleImageView = itemView.civSearchAvatar
    }
}