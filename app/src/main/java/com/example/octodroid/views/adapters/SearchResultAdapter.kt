package com.example.octodroid.views.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.example.octodroid.data.GitHub
import com.example.octodroid.views.components.LinearLayoutLoadMoreListener
import com.example.octodroid.views.helpers.ToastHelper
import com.example.octodroid.views.holders.ProgressViewHolder
import com.example.octodroid.views.holders.RepositoryItemViewHolder
import com.jakewharton.rxbinding.view.RxView
import com.rejasupotaro.octodroid.http.Response
import com.rejasupotaro.octodroid.models.Repository
import com.rejasupotaro.octodroid.models.SearchResult

import java.util.ArrayList

import rx.Observable
import rx.Subscriber
import rx.subjects.BehaviorSubject

class SearchResultAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private object ViewType {
        private val ITEM = 1
        private val FOOTER = 2
    }

    private val repositories = ArrayList<Repository>()
    private var responseSubject: BehaviorSubject<Observable<Response<SearchResult<Repository>>>>? = null
    private var pagedResponse: Observable<Response<SearchResult<Repository>>>? = null

    init {
        recyclerView.setVisibility(View.GONE)

        val layoutManager = LinearLayoutManager(recyclerView.getContext())
        recyclerView.setHasFixedSize(false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addOnScrollListener(object : LinearLayoutLoadMoreListener(layoutManager) {
            fun onLoadMore() {
                if (pagedResponse != null) {
                    responseSubject!!.onNext(pagedResponse)
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ViewType.FOOTER) {
            return ProgressViewHolder.create(parent)
        } else {
            return RepositoryItemViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.FOOTER -> {
            }
            else -> {
                val repository = repositories.get(position)
                (viewHolder as RepositoryItemViewHolder).bind(repository)
            }
        }// do nothing
    }

    override fun getItemViewType(position: Int): Int {
        if (repositories.size == 0 || position == repositories.size) {
            return ViewType.FOOTER
        } else {
            return ViewType.ITEM
        }
    }

    override fun getItemCount(): Int {
        return repositories.size + 1
    }

    fun clear() {
        repositories.clear()
        notifyDataSetChanged()
    }

    fun submit(query: String) {
        clear()
        recyclerView.setVisibility(View.VISIBLE)

        responseSubject = BehaviorSubject.create(GitHub.client().searchRepositories(query))
        responseSubject!!.takeUntil(RxView.detaches(recyclerView)).flatMap({ r -> r }).subscribe(ResponseSubscriber())
    }

    private inner class ResponseSubscriber : Subscriber<Response<SearchResult<Repository>>>() {

        override fun onCompleted() {
            unsubscribe()
        }

        override fun onError(e: Throwable) {
            ToastHelper.showError(recyclerView.getContext())
        }

        override fun onNext(r: Response<SearchResult<Repository>>) {
            if (r.entity().getItems() == null || r.entity().getItems().isEmpty()) {
                return
            }

            val items = r.entity().getItems()
            val startPosition = repositories.size
            repositories.addAll(items)
            notifyItemRangeInserted(startPosition, items.size)

            pagedResponse = r.next()
        }
    }
}