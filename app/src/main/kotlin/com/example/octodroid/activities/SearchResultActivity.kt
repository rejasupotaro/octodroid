package com.example.octodroid.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.views.adapters.SearchResultAdapter

class SearchResultActivity : AppCompatActivity() {
    val searchResultListView: RecyclerView by bindView(R.id.repository_list)

    private var searchResultAdapter: SearchResultAdapter? = null

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            if (!TextUtils.isEmpty(query)) {
                searchResultAdapter!!.submit(query)
            }
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        setupActionBar()
        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_result, menu)

        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.isSubmitButtonEnabled = false
        searchView.queryHint = getString(R.string.title_search_result)
        searchView.setOnQueryTextListener(onQueryTextListener)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupViews() {
        searchResultAdapter = SearchResultAdapter(searchResultListView)
        searchResultListView.adapter = searchResultAdapter
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SearchResultActivity::class.java)
            context.startActivity(intent)
        }
    }
}
