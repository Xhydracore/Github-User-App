package com.submission.githubuserapp.activity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.DetailPagerAdapter
import com.submission.githubuserapp.helper.DatabaseContract
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.GITHUB_USERS_URI
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.USER_ID
import com.submission.githubuserapp.helper.FavoriteHelper
import com.submission.githubuserapp.helper.MappingHelper
import com.submission.githubuserapp.model.DetailUserResponse
import com.submission.githubuserapp.model.Favorite
import com.submission.githubuserapp.model.User
import com.submission.githubuserapp.presenter.DetailUsernamePresenter
import com.submission.githubuserapp.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter
    private lateinit var favoriteHelper: FavoriteHelper
    private var favorite: Favorite? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // get bundle data from parcelable
        val bundle = intent.getBundleExtra("detail_user")
        val user = bundle.getParcelable("user") as? User

        // fav sqlite
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favorite = Favorite()

        // declare
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(user?.username)

        // setup actionbar
        setupNavigation()

        // setup actionBar
        setupActionBar(user?.username.toString())

        // back button
        intentMainActivity.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun setupActionBar(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, username)
        vpDetail.adapter = detailPagerAdapter
        tlDetail.setupWithViewPager(vpDetail)
    }

    override fun onShowLoading() {
        ivDetailUserProfile.visibility=View.GONE
        tvDetailName.visibility=View.GONE
        tvDetailUserName.visibility=View.GONE
        tvDetailFollowers.visibility=View.GONE
        tvDetailFollowing.visibility=View.GONE
        tvDetailRepo.visibility=View.GONE
        tvDetailLocation.visibility=View.GONE
        tvDetailCompany.visibility=View.GONE
        icCompany.visibility=View.GONE
        icLocation.visibility=View.GONE
        DetailFollowers.visibility=View.GONE
        DetailFollowing.visibility=View.GONE
        DetailRepo.visibility=View.GONE
        DetailSpinner.visibility= View.VISIBLE
        btnFavoriteUser.hide()
    }

    override fun onHideLoading() {
        ivDetailUserProfile.visibility=View.VISIBLE
        tvDetailName.visibility=View.VISIBLE
        tvDetailUserName.visibility=View.VISIBLE
        tvDetailFollowers.visibility=View.VISIBLE
        tvDetailFollowing.visibility=View.VISIBLE
        tvDetailRepo.visibility=View.VISIBLE
        tvDetailLocation.visibility=View.VISIBLE
        tvDetailCompany.visibility=View.VISIBLE
        icCompany.visibility=View.VISIBLE
        icLocation.visibility=View.VISIBLE
        DetailFollowers.visibility=View.VISIBLE
        DetailFollowing.visibility=View.VISIBLE
        DetailRepo.visibility=View.VISIBLE
        DetailSpinner.visibility= View.GONE
        btnFavoriteUser.show()
    }

    override fun onSuccessGetDetailUsername(response: DetailUserResponse?) {
        Glide.with(this)
            .load(response?.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(ivDetailUserProfile)
        tvDetailName.text = response?.name
        tvDetailCompany.text = response?.company
        tvDetailUserName.text = response?.login
        tvDetailLocation.text = response?.location
        tvDetailRepo.text = response?.publicRepos.toString()
        tvDetailFollowers.text = response?.followers.toString()
        tvDetailFollowing.text = response?.following.toString()

        //check state Favorite
        isFavorite = isFavoriteUser(response?.id?.toLong())
        setFavorite()

        btnFavoriteUser.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite(response?.login.toString())
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

    private fun setFavorite() {
        if (isFavorite) {
            btnFavoriteUser.setImageResource(R.drawable.ic_added_favorite)
        } else {
            btnFavoriteUser.setImageResource(R.drawable.ic_add_favorite)
        }
    }

    private fun addToFavorite(login: String, avatar: String, id:Long?) {
        val values = ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME, login)
        values.put(DatabaseContract.FavoriteColumns.AVATAR_URL, avatar)
        values.put(USER_ID, id)
        val cr = contentResolver.insert(GITHUB_USERS_URI, values)
        Log.e("saved", "${cr?.lastPathSegment}")
    }

    private fun removeFromFavorite(userID: String) {
        val uri = Uri.parse("$GITHUB_USERS_URI/$userID")
        contentResolver.delete(uri, null, null)
    }
    private fun isFavoriteUser(userID: Long?): Boolean {
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
