package com.example.octodroid.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.intent.RequestCode
import com.example.octodroid.views.adapters.RepositoryAdapter

class RepositoryListActivity : AppCompatActivity() {
    val repositoryListView: RecyclerView by bindView(R.id.repository_list)

    private var repositoryAdapter: RepositoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)

        setupActionBar()
        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        supportActionBar!!.title = getString(R.string.title_repository_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViews() {
        repositoryAdapter = RepositoryAdapter(repositoryListView)
        repositoryListView.adapter = repositoryAdapter
    }

    companion object {

        fun launch(activity: Activity) {
            val intent = Intent(activity, RepositoryListActivity::class.java)
            activity.startActivityForResult(intent, RequestCode.ADD_REPOSITORY)
        }
    }
}
