package com.submission.githubuserapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.githubuserapp.R
import com.submission.githubuserapp.activity.UserDetailActivity
import com.submission.githubuserapp.model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter (val context: Context, val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.tvName.text = user.name
        holder.tvRepo.text = user.repository
        holder.tvFollower.text = user.follower
        holder.tvFollowing.text = user.following
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.imgUser)
        holder.cardIntent.setOnClickListener {
            val intent = Intent(context, UserDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            intent.putExtra("detail_user", bundle)
            context.startActivity(intent)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.tvName
        var imgUser: CircleImageView = itemView.civAvatar
        var tvRepo: TextView = itemView.tvRepo
        var tvFollower: TextView = itemView.tvFollowers
        var tvFollowing: TextView = itemView.tvFollowing
        var cardIntent: ConstraintLayout = itemView.intentDetail
    }
}