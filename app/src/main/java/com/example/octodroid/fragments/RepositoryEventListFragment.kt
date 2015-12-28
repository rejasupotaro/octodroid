package com.example.octodroid.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.octodroid.R
import com.example.octodroid.data.converters.RepositoryConverter
import com.example.octodroid.views.adapters.RepositoryEventListAdapter
import com.rejasupotaro.octodroid.models.Repository
import com.yatatsu.autobundle.Arg
import com.yatatsu.autobundle.AutoBundle

import butterknife.Bind
import butterknife.ButterKnife

class RepositoryEventListFragment : Fragment() {
    @Bind(R.id.repository_event_list)
    internal var repositoryEventListView: RecyclerView

    @Arg(converter = RepositoryConverter::class)
    internal var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutoBundle.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_repository_event_list, null)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
    }

    override fun onDestroyView() {
        ButterKnife.unbind(this)
        super.onDestroyView()
    }

    private fun setupViews() {
        val repositoryEventListAdapter = RepositoryEventListAdapter(repositoryEventListView)
        repositoryEventListView.setAdapter(repositoryEventListAdapter)
        repositoryEventListAdapter.requestRepositoryEvents(repository)
    }
}
