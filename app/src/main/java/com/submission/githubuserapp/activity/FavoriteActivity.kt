package com.submission.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.FavoriteUserAdapter
import com.submission.githubuserapp.helper.FavoriteHelper
import com.submission.githubuserapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // setup action bar
        setupNavigation()

        // back to main
        intentMainActivity.setOnClickListener {
            finish()
        }

        // rv
        rvFavorite.layoutManager = LinearLayoutManager(this)
        rvFavorite.setHasFixedSize(true)
        adapter = FavoriteUserAdapter(this)
        rvFavorite.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        // get data
        loadFavoriteAsync()
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            // shimmer visible here
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            // shimmer gone here
            val favorites = deferredFavorite.await()
            if (favorites.size > 0) {
                adapter.listFavorite = favorites
                adapter.notifyDataSetChanged()
            } else {
                adapter.listFavorite = ArrayList()
                toast("No Favorite here")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}