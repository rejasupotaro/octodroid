package com.example.octodroid.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.data.SessionManager
import com.example.octodroid.data.prefs.OctodroidPrefsSchema
import com.example.octodroid.fragments.RepositoryEventListFragment
import com.example.octodroid.intent.RequestCode
import com.example.octodroid.views.components.ViewPagerAdapter
import com.rejasupotaro.octodroid.models.Repository
import com.rejasupotaro.octodroid.models.Resource
import java.util.*

class MainActivity : AppCompatActivity() {
    val tabLayout: TabLayout by bindView(R.id.repository_view_pager_tabs)
    val viewPager: ViewPager by bindView(R.id.repository_view_pager)
    val emptyViewContainer: View by bindView(R.id.empty_view_container)

    private val repositories = ArrayList<Repository>()

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            val repository = repositories.get(position)
            setTitle(repository)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SessionManager.isLoggedIn(this)) {
            SessionManager.login(this)
            setupViews()
        } else {
            LoginActivity.launch(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> return true
            R.id.action_add_repository -> {
                RepositoryListActivity.launch(this)
                return true
            }
            R.id.action_logout -> {
                SessionManager.logout(this)
                LoginActivity.launch(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RequestCode.ADD_REPOSITORY -> setupViews()
            else -> {
            }
        }
    }

    private fun setTitle(repository: Repository) {
        supportActionBar!!.title = repository.fullName
    }

    private fun setupViews() {
        val octodroidPrefs = OctodroidPrefsSchema.get(this)
        if (octodroidPrefs.selectedSerializedRepositories.isEmpty()) {
            emptyViewContainer.visibility = View.VISIBLE

            title = ""
        } else {
            repositories.clear()

            emptyViewContainer.visibility = View.GONE

            val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
            for (serializedRepositories in octodroidPrefs.selectedSerializedRepositories) {
                val repository = Resource.fromJson(serializedRepositories, Repository::class.java)

                val fragment = RepositoryEventListFragment.newInstance(repository)

                pagerAdapter.addFragment(fragment, repository.name)
                repositories.add(repository)
            }

            viewPager.adapter = pagerAdapter
            viewPager.addOnPageChangeListener(onPageChangeListener)
            tabLayout.setupWithViewPager(viewPager)

            setTitle(repositories.get(0))
        }

        findViewById(R.id.add_repository_button).setOnClickListener {
            RepositoryListActivity.launch(this)
        }
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
