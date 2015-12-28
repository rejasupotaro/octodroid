package com.example.octodroid.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.MenuItem

import com.example.octodroid.R
import com.example.octodroid.intent.RequestCode
import com.example.octodroid.views.adapters.RepositoryAdapter
import com.yatatsu.autobundle.Arg
import com.yatatsu.autobundle.AutoBundle

import butterknife.Bind
import butterknife.ButterKnife

class RepositoryListActivity : AppCompatActivity() {
    @Bind(R.id.repository_list)
    internal var repositoryListView: RecyclerView

    private var repositoryAdapter: RepositoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        ButterKnife.bind(this)
        AutoBundle.bind(this)

        setupActionBar()
        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        repositoryAdapter!!.saveSelectedRepositories()
        super.onBackPressed()
    }

    fun setupActionBar() {
        getSupportActionBar()!!.setTitle(getString(R.string.title_repository_list))
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViews() {
        repositoryAdapter = RepositoryAdapter(repositoryListView)
        repositoryListView.setAdapter(repositoryAdapter)
    }

    companion object {

        fun launch(activity: Activity) {
            val intent = Intent(activity, RepositoryListActivity::class.java)
            activity.startActivityForResult(intent, RequestCode.ADD_REPOSITORY)
        }
    }
}
