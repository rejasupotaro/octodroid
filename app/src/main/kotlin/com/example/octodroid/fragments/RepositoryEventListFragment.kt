package com.example.octodroid.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.example.octodroid.R
import com.example.octodroid.views.adapters.RepositoryEventListAdapter
import com.rejasupotaro.octodroid.models.Repository
import com.rejasupotaro.octodroid.models.Resource

class RepositoryEventListFragment : Fragment() {
    val repositoryEventListView: RecyclerView by bindView(R.id.repository_event_list)

    var repository: Repository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serializedRepository = arguments.getString("extra_repository")
        repository = Resource.fromJson(serializedRepository, Repository::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_repository_event_list, null)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val repositoryEventListAdapter = RepositoryEventListAdapter(repositoryEventListView)
        repositoryEventListView.adapter = repositoryEventListAdapter
        repositoryEventListAdapter.requestRepositoryEvents(repository!!)
    }

    companion object {
        fun newInstance(repository: Repository): RepositoryEventListFragment {
            val fragment = RepositoryEventListFragment()
            val bundle = Bundle()
            bundle.putString("extra_repository", repository.toJson())
            fragment.arguments = bundle
            return fragment
        }
    }
}
