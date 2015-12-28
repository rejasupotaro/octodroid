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

import com.example.octodroid.R
import com.example.octodroid.data.SessionManager
import com.example.octodroid.data.prefs.OctodroidPrefs
import com.example.octodroid.data.prefs.OctodroidPrefsSchema
import com.example.octodroid.fragments.RepositoryEventListFragment
import com.example.octodroid.fragments.RepositoryEventListFragmentAutoBundle
import com.example.octodroid.intent.RequestCode
import com.example.octodroid.views.components.ViewPagerAdapter
import com.rejasupotaro.octodroid.models.Repository
import com.rejasupotaro.octodroid.models.Resource

import java.util.ArrayList

import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {
    @Bind(R.id.repository_view_pager_tabs)
    internal var tabLayout: TabLayout
    @Bind(R.id.repository_view_pager)
    internal var viewPager: ViewPager
    @Bind(R.id.empty_view_container)
    internal var emptyViewContainer: View

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
        ButterKnife.bind(this)

        if (SessionManager.isLoggedIn(this)) {
            SessionManager.login(this)
            setupViews()
        } else {
            LoginActivity.launch(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            RequestCode.ADD_REPOSITORY -> setupViews()
            else -> {
            }
        }// do nothing
    }

    private fun setTitle(repository: Repository) {
        getSupportActionBar()!!.setTitle(repository.getFullName())
    }

    private fun setupViews() {
        val octodroidPrefs = OctodroidPrefsSchema.get(this)
        if (octodroidPrefs.getSeletedSerializedRepositories().isEmpty()) {
            emptyViewContainer.setVisibility(View.VISIBLE)

            setTitle("")
        } else {
            repositories.clear()

            emptyViewContainer.setVisibility(View.GONE)

            val pagerAdapter = ViewPagerAdapter(getSupportFragmentManager())
            for (serializedRepositories in octodroidPrefs.getSeletedSerializedRepositories()) {
                val repository = Resource.fromJson(serializedRepositories, Repository::class.java)

                val fragment = RepositoryEventListFragmentAutoBundle.createFragmentBuilder(repository).build()

                pagerAdapter.addFragment(fragment, repository.getName())
                repositories.add(repository)
            }

            viewPager.setAdapter(pagerAdapter)
            viewPager.addOnPageChangeListener(onPageChangeListener)
            tabLayout.setupWithViewPager(viewPager)

            setTitle(repositories.get(0))
        }
    }

    @OnClick(R.id.add_repository_button)
    internal fun onAddRepositoryButtonClick() {
        RepositoryListActivity.launch(this)
    }

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
