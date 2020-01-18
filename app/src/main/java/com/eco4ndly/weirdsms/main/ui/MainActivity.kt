package com.eco4ndly.weirdsms.main.ui

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.eco4ndly.weirdsms.R
import com.eco4ndly.weirdsms.base.BaseActivity
import com.eco4ndly.weirdsms.infra.extension.isDefaultSmsApp
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainActivityViewModel>() {

    lateinit var mainActivityViewModel: MainActivityViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var messageListAdapter: MessageListAdapter

    override fun provideViewModel(): MainActivityViewModel {
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        return mainActivityViewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun takeOff() {
        message_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = messageListAdapter
            setHasFixedSize(true)
        }

        viewModel.messageListLiveData().observe(this, Observer {
            messageListAdapter.messageModelList = it
        })

        viewModel.getMessagesV2()

    }

    override fun onResume() {
        super.onResume()
        if(!isDefaultSmsApp(this)) {
            showMakeDefaultSmsAppDialog()
        } else {
            viewModel.syncDatabase()
        }
    }

    override fun showLoader() {
        progress_bar_main.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progress_bar_main.visibility = View.GONE
    }
}
