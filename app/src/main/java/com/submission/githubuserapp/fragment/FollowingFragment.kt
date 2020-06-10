package com.submission.githubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapp.R
import com.submission.githubuserapp.adapter.FollowAdapter
import com.submission.githubuserapp.model.DefaultItemDecorator
import com.submission.githubuserapp.model.FollowResponse
import com.submission.githubuserapp.presenter.FollowPresenter
import com.submission.githubuserapp.view.FollowView
import kotlinx.android.synthetic.main.fragment_following.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment(), FollowView {

    private val ARG_USERNAME = "username"

    fun newInstance(username: String) : FollowingFragment {
        val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.fragment_following, container, false)
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
        followPresenter.getFollowing(username)
    }

    override fun onShowLoading() {
        lottieFollowingSpinner.visibility = View.VISIBLE
        lottieFollowingSpinner.playAnimation()
    }

    override fun onHideLoading() {
        lottieFollowingSpinner.cancelAnimation()
        lottieFollowingSpinner.visibility = View.GONE
    }

    override fun onSuccessFollow(data: List<FollowResponse?>?) {
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.addItemDecoration(
            DefaultItemDecorator(
                resources.getDimensionPixelSize(R.dimen.provider_name_horizontal_margin),
                resources.getDimensionPixelSize(R.dimen.provide_name_vertical_margin)
            )
        )
        rvFollowing.adapter = FollowAdapter(activity, data)
    }

    override fun onErrorFollower(message: String) {
        toast(message)
    }
}