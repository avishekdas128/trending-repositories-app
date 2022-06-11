package com.orangeink.trending.ui.trending

import android.animation.Animator
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.orangeink.trending.databinding.ActivityTrendingBinding
import com.orangeink.trending.ui.adapter.RepositoryAdapter
import com.orangeink.trending.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrendingBinding

    private val repositoryAdapter: RepositoryAdapter by lazy { RepositoryAdapter() }
    private val viewModel: TrendingViewModel by viewModels()

    private var mBundleRecyclerViewState: Bundle? = null
    private var mListState: Parcelable? = null

    private var apiCallRequired: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setListeners()
        setupNetworkMonitor()
        subscribeToLiveData()
    }


    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        mListState = binding.rvRepository.layoutManager?.onSaveInstanceState()
        mBundleRecyclerViewState?.putParcelable(Constants.KEY_RECYCLER_STATE, mListState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (mBundleRecyclerViewState != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                mListState = mBundleRecyclerViewState!!.getParcelable(Constants.KEY_RECYCLER_STATE)
                binding.rvRepository.layoutManager?.onRestoreInstanceState(mListState)
            }, 50)
        }
    }

    private fun initViews() {
        if (hasInternetConnection()) {
            binding.progressTrending.visibility = View.VISIBLE
            viewModel.getRemoteRepositories()
        }
        binding.rvRepository.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }

    private fun setListeners() {
        binding.ivSearch.setOnClickListener { openSearch() }
        binding.ivCloseSearch.setOnClickListener { closeSearch() }
    }

    private fun setupNetworkMonitor() {
        viewModel.networkState.observe(this) {
            when (it) {
                NetworkStatus.Available -> {
                    binding.tvOffline.visibility = View.GONE
                    binding.noNetwork.noNetwork.visibility = View.GONE
                    if (apiCallRequired) {
                        binding.progressTrending.visibility = View.VISIBLE
                        viewModel.getRemoteRepositories()
                    }
                }
                NetworkStatus.Unavailable -> binding.tvOffline.visibility = View.VISIBLE
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.trendingRepositories.observe(this) {
            binding.progressTrending.visibility = View.GONE
            if (it.isNotEmpty()) {
                binding.noNetwork.noNetwork.visibility = View.GONE
                repositoryAdapter.repository = it
                repositoryAdapter.submitList(it)
                if (!hasInternetConnection()) {
                    binding.tvOffline.visibility = View.VISIBLE
                    apiCallRequired = false
                }
            } else {
                if (!hasInternetConnection()) {
                    binding.noNetwork.noNetwork.visibility = View.VISIBLE
                    apiCallRequired = true
                } else binding.progressTrending.visibility = View.VISIBLE
            }
        }
        viewModel.error.observe(this) {
            binding.progressTrending.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSearch() {
        binding.searchInputText.setText("")
        binding.searchInputText.doAfterTextChanged {
            repositoryAdapter.filter = it.toString()
        }
        binding.searchInputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(this)
                return@setOnEditorActionListener true
            }
            false
        }
        binding.searchOpenView.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.ivSearch.right + binding.ivSearch.left) / 2,
            (binding.ivSearch.top + binding.ivSearch.bottom) / 2,
            0f, binding.rootFrameLayout.width.toFloat()
        )
        circularReveal.duration = 300
        circularReveal.start()
        showKeyboard(this, binding.searchInputText)
    }

    private fun closeSearch() {
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.ivSearch.right + binding.ivSearch.left) / 2,
            (binding.ivSearch.top + binding.ivSearch.bottom) / 2,
            binding.rootFrameLayout.width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                hideKeyboard(this@MainActivity)
                binding.searchOpenView.visibility = View.INVISIBLE
                binding.searchInputText.setText("")
                circularConceal.removeAllListeners()
            }
        })
    }

}