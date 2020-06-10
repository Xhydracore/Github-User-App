package com.submission.githubuserapp.view

import com.submission.githubuserapp.model.SearchItem

interface SearchUsernameView {
    fun onShowLoading()
    fun onHideLoading()
    fun onShowNothing(message: String)
    fun onHideNothing()
    fun onSuccessGetSearch(data: List<SearchItem?>?)
    fun onErrorGetSearch(message: String)
}