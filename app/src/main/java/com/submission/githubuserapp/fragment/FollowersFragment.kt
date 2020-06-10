package com.submission.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.FollowAdapter
import com.submission.githubuserapp.model.DefaultItemDecorator
import com.submission.githubuserapp.model.FollowResponse
import com.submission.githubuserapp.presenter.FollowPresenter
import com.submission.githubuserapp.view.FollowView
import kotlinx.android.synthetic.main.fragment_followers.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment(), FollowView {

    private val ARG_USERNAME = "username"

    fun newInstance(username: String) : FollowersFragment {
        val fragment = FollowersFragment()
        val bundle = Bundle()
        bundle.putString(ARG_USERNAME, username)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private lateinit var followPresenter: FollowPresenter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        // request
        followPresenter = FollowPresenter(this)
        followPresenter.getFollower(username)
    }

    override fun onShowLoading() {
        lottieFollowersSpinner.visibility = View.VISIBLE
        lottieFollowersSpinner.playAnimation()
    }

    override fun onHideLoading() {
        lottieFollowersSpinner.cancelAnimation()
        lottieFollowersSpinner.visibility = View.GONE
    }

    override fun onSuccessFollow(data: List<FollowResponse?>?) {
        rvFollowers.layoutManager = LinearLayoutManager(activity)
        rvFollowers.addItemDecoration(
            DefaultItemDecorator(
                resources.getDimensionPixelSize(R.dimen.provider_name_horizontal_margin),
                resources.getDimensionPixelSize(R.dimen.provide_name_vertical_margin)
            )
        )
        rvFollowers.adapter = FollowAdapter(activity, data)
    }

    override fun onErrorFollower(message: String) {
        toast(message)
    }

}