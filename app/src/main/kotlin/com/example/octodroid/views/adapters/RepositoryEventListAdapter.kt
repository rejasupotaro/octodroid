package com.example.octodroid.views.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.octodroid.data.GitHub
import com.example.octodroid.views.components.DividerItemDecoration
import com.example.octodroid.views.components.LinearLayoutLoadMoreListener
import com.example.octodroid.views.holders.EventItemViewHolder
import com.example.octodroid.views.holders.ProgressViewHolder
import com.jakewharton.rxbinding.view.RxView
import com.rejasupotaro.octodroid.http.Response
import com.rejasupotaro.octodroid.models.Event
import com.rejasupotaro.octodroid.models.Repository
import rx.Observable
import rx.subjects.BehaviorSubject
import java.util.*

class RepositoryEventListAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private object ViewType {
        val ITEM = 1
        val FOOTER = 2
    }

    private val context: Context
    private val events = ArrayList<Event>()
    private var responseSubject: BehaviorSubject<Observable<Response<List<Event>>>>? = null
    private var pagedResponse: Observable<Response<List<Event>>>? = null
    private var isReachedLast: Boolean = false

    init {
        this.context = recyclerView.context

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(false)
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(DividerItemDecoration(context))
        recyclerView.addOnScrollListener(object : LinearLayoutLoadMoreListener(layoutManager) {
            override fun onLoadMore() {
                if (pagedResponse != null) {
                    responseSubject!!.onNext(pagedResponse)
                }
            }
        })
    }

    fun requestRepositoryEvents(repository: Repository) {
        val owner = repository.owner.login
        val repo = repository.name

        responseSubject = BehaviorSubject.create(GitHub.client().repositoryEvents(owner, repo))
        responseSubject!!.takeUntil(RxView.detaches(recyclerView))
                .flatMap { r -> r }
                .subscribe({ r ->
                    if (r.entity().isEmpty()) {
                        isReachedLast = true
                        notifyDataSetChanged()
                        return@subscribe
                    }

                    val items = r.entity()
                    val startPosition = events.size
                    events.addAll(items)

                    if (r.hasNext()) {
                        pagedResponse = r.next()
                    } else {
                        isReachedLast = true
                    }

                    if (startPosition == 0) {
                        notifyDataSetChanged()
                    } else {
                        notifyItemRangeInserted(startPosition, items.size)
                    }
                })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ViewType.ITEM -> return EventItemViewHolder.create(parent)
            else -> return ProgressViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.ITEM -> {
                val event = events[position]
                (viewHolder as EventItemViewHolder).bind(event)
            }
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (events.size == 0 || position == events.size) {
            return ViewType.FOOTER
        } else {
            return ViewType.ITEM
        }
    }

    override fun getItemCount(): Int {
        return events.size + if (isReachedLast) 0 else 1
    }
}

