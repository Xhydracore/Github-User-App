package com.submission.githubuserapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.ListUserAdapter
import com.submission.githubuserapp.model.DefaultItemDecorator
import com.submission.githubuserapp.model.User
import com.submission.githubuserapp.model.UserData
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private var list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottomAppBar)
        // load recyclerView Function
        loadItemUserRecycler()


        btnintentSearchActivity.setOnClickListener {
            startActivity<SearchActivity>()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.language_setting -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.action_settings -> startActivity(Intent(this, SettingActivity::class.java))
            R.id.action_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadItemUserRecycler() {
        rvUser = rvGithubUser
        rvUser.setHasFixedSize(true)
        list.addAll(UserData.listData)
        rvUser.addItemDecoration(
                DefaultItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.provider_name_horizontal_margin),
                    resources.getDimensionPixelSize(R.dimen.provide_name_vertical_margin)
                )
                )
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(this, list)
        rvUser.adapter = listUserAdapter
    }

}