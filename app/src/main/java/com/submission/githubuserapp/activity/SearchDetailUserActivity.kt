package com.submission.githubuserapp.activity

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.DetailPagerAdapter
import com.submission.githubuserapp.helper.DatabaseContract
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.GITHUB_USERS_URI
import com.submission.githubuserapp.helper.FavoriteHelper
import com.submission.githubuserapp.helper.MappingHelper
import com.submission.githubuserapp.model.DetailUserResponse
import com.submission.githubuserapp.model.Favorite
import com.submission.githubuserapp.presenter.DetailUsernamePresenter
import com.submission.githubuserapp.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_search_detail_user.*
import kotlinx.android.synthetic.main.activity_search_detail_user.DetailFollowers
import kotlinx.android.synthetic.main.activity_search_detail_user.DetailFollowing
import kotlinx.android.synthetic.main.activity_search_detail_user.DetailRepo
import kotlinx.android.synthetic.main.activity_search_detail_user.icCompany
import kotlinx.android.synthetic.main.activity_search_detail_user.icLocation
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList

class SearchDetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter
    private lateinit var favoriteHelper: FavoriteHelper
    private var favorite: Favorite? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail_user)

        // getString
        val username = intent.getStringExtra("username") as String

        // setup actionBar
        setupActionBar()

        // sqlite
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favorite = Favorite()

        // setup viewPager
        setupViewPager(username)

        // check isFavorite
        favoriteState(username)
        setFavorite()

        // declare presenter
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(username)

        // back button
        intentSearchActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun setupViewPager(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, username)
        vpSearchDetail.adapter = detailPagerAdapter
        tlSearchDetail.setupWithViewPager(vpSearchDetail)
    }

    override fun onShowLoading() {
        ivDetailSearchUserProfile.visibility=View.GONE
        tvDetailSearchName.visibility=View.GONE
        tvDetailSearchUserName.visibility=View.GONE
        tvDetailSearchFollowers.visibility=View.GONE
        tvDetailSearchFollowing.visibility=View.GONE
        tvDetailSearchRepo.visibility=View.GONE
        tvDetailSearchLocation.visibility=View.GONE
        tvDetailSearchCompany.visibility=View.GONE
        icCompany.visibility=View.GONE
        icLocation.visibility=View.GONE
        DetailFollowers.visibility=View.GONE
        DetailFollowing.visibility=View.GONE
        DetailRepo.visibility=View.GONE
        DetailSearchSpinner.visibility= View.VISIBLE
        btnFavoriteSearchUser.hide()
    }

    override fun onHideLoading() {

        ivDetailSearchUserProfile.visibility=View.VISIBLE
        tvDetailSearchName.visibility=View.VISIBLE
        tvDetailSearchUserName.visibility=View.VISIBLE
        tvDetailSearchFollowers.visibility=View.VISIBLE
        tvDetailSearchFollowing.visibility=View.VISIBLE
        tvDetailSearchRepo.visibility=View.VISIBLE
        tvDetailSearchLocation.visibility=View.VISIBLE
        tvDetailSearchCompany.visibility=View.VISIBLE
        icCompany.visibility=View.VISIBLE
        icLocation.visibility=View.VISIBLE
        DetailFollowers.visibility=View.VISIBLE
        DetailFollowing.visibility=View.VISIBLE
        DetailRepo.visibility=View.VISIBLE
        DetailSearchSpinner.visibility= View.GONE
        btnFavoriteSearchUser.show()
    }

    override fun onSuccessGetDetailUsername(response: DetailUserResponse?) {
        tvDetailSearchLocation.text = response?.location
        Glide.with(this)
            .load(response?.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(ivDetailSearchUserProfile)
        tvDetailSearchName.text = response?.name
        tvDetailSearchCompany.text = response?.company
        tvDetailSearchUserName.text = response?.login
        tvDetailSearchRepo.text = response?.publicRepos.toString()
        tvDetailSearchFollowers.text = response?.followers.toString()
        tvDetailSearchFollowing.text = response?.following.toString()

        btnFavoriteSearchUser.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite(response?.id?.toLong())
            } else {
                addToFavorite(response?.login.toString(), response?.avatarUrl.toString(),
                    response?.id?.toLong()
                )
            }
            isFavorite = !isFavorite
            setFavorite()
        }
    }

    override fun onErrorGetDetailUsername(message: String) {

    }

    private fun favoriteState(userID: String) {
        val check = favoriteHelper.queryByUsername(userID)
        val result = check.parseList(classParser<Favorite>())
        if (result.isNotEmpty()) isFavorite = true
    }

    private fun setFavorite() {
        if (isFavorite) {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_added_favorite)
        } else {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_add_favorite)
        }
    }

    private fun addToFavorite(login: String, avatar: String, id: Long?) {
        val values = ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME, login)
        values.put(DatabaseContract.FavoriteColumns.AVATAR_URL, avatar)
        values.put(DatabaseContract.FavoriteColumns.USER_ID, id)
        val cr = contentResolver.insert(GITHUB_USERS_URI, values)
        Log.e("saved", "${cr?.lastPathSegment}")
    }

    private fun removeFromFavorite(userID: Long?) {
        val uri = Uri.parse("$GITHUB_USERS_URI/$userID")
        contentResolver.delete(uri, null, null)
    }
    private fun isFavoriteUser(userID: Long): Boolean {
        val uri = Uri.parse("$GITHUB_USERS_URI/$userID")
        val cursor = contentResolver.query(uri, null, null, null, null)
        Log.e("isFavoriteUser", "found:${cursor?.count} || cursor:${cursor}")
        if (cursor != null && cursor.count != 0) {
            val data = MappingHelper.mapCursorToObject(cursor)
            Log.e("isFavoriteUser", "username:${data.login}")
            cursor.close()
            return true
        }
        return false
    }
}