package com.submission.githubuserapp.activity

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.SearchUsernameAdapter
import com.submission.githubuserapp.model.DefaultItemDecorator
import com.submission.githubuserapp.model.SearchItem
import com.submission.githubuserapp.presenter.SearchUsernamePresenter
import com.submission.githubuserapp.view.SearchUsernameView
import kotlinx.android.synthetic.main.activity_favorite.intentMainActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity(), SearchUsernameView {

    private lateinit var searchPresenter: SearchUsernamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // setupActionBar
        setupActionBar()

        intentMainActivity.setOnClickListener {
            finish()
        }
        // declare presenter
        searchPresenter = SearchUsernamePresenter(this)

        // text search result
        svSearchResult.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                    rvSearchResult.visibility = View.GONE
                    tvNullData.visibility = View.GONE
                    searchPresenter.getSearchResult(query)
                    return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    override fun onShowLoading() {
        searchSpinner.visibility = View.VISIBLE
        searchSpinner.playAnimation()
    }

    override fun onHideLoading() {
        searchSpinner.visibility = View.GONE
        searchSpinner.cancelAnimation()
    }

    override fun onShowNothing(message: String) {
        tvNullData.visibility = View.VISIBLE
        tvNullData.text = message
        rvSearchResult.visibility = View.GONE
    }

    override fun onHideNothing() {
        tvNullData.visibility = View.GONE
        rvSearchResult.visibility = View.VISIBLE
    }

    override fun onSuccessGetSearch(data: List<SearchItem?>?) {
        rvSearchResult.layoutManager = LinearLayoutManager(this)
        rvSearchResult.addItemDecoration(
            DefaultItemDecorator(
                resources.getDimensionPixelSize(R.dimen.provider_name_horizontal_margin),
                resources.getDimensionPixelSize(R.dimen.provide_name_vertical_margin)
            )
        )
        rvSearchResult.adapter = SearchUsernameAdapter(this, data)
        rvSearchResult.setHasFixedSize(true)
    }

    override fun onErrorGetSearch(message: String) {
        toast(message)
    }
}