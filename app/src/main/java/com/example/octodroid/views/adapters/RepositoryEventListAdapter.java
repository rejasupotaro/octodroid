package com.example.octodroid.views.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.data.GitHub;
import com.example.octodroid.views.components.DividerItemDecoration;
import com.example.octodroid.views.components.LinearLayoutLoadMoreListener;
import com.example.octodroid.views.holders.EventItemViewHolder;
import com.example.octodroid.views.holders.ProgressViewHolder;
import com.jakewharton.rxbinding.view.RxView;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Event;
import com.rejasupotaro.octodroid.models.Repository;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class RepositoryEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static class ViewType {
        private static final int ITEM = 1;
        private static final int FOOTER = 2;
    }

    private Context context;
    private RecyclerView recyclerView;
    private List<Event> events = new ArrayList<>();
    private BehaviorSubject<Observable<Response<List<Event>>>> responseSubject;
    private Observable<Response<List<Event>>> pagedResponse;
    private boolean isReachedLast;

    public RepositoryEventListAdapter(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();
        this.recyclerView = recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(context));
        recyclerView.addOnScrollListener(new LinearLayoutLoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (pagedResponse != null) {
                    responseSubject.onNext(pagedResponse);
                }
            }
        });
    }

    public void requestRepositoryEvents(Repository repository) {
        String owner = repository.getOwner().getLogin();
        String repo = repository.getName();

        responseSubject = BehaviorSubject.create(GitHub.client().repositoryEvents(owner, repo));
        responseSubject.takeUntil(RxView.detaches(recyclerView))
                .flatMap(r -> r)
                .subscribe(r -> {
                    if (r.entity().isEmpty()) {
                        isReachedLast = true;
                        notifyDataSetChanged();
                        return;
                    }

                    List<Event> items = r.entity();
                    int startPosition = events.size();
                    events.addAll(items);

                    if (r.hasNext()) {
                        pagedResponse = r.next();
                    } else {
                        isReachedLast = true;
                    }

                    if (startPosition == 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemRangeInserted(startPosition, items.size());
                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.ITEM:
                return EventItemViewHolder.create(parent);
            default:
                return ProgressViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ViewType.ITEM:
                Event event = events.get(position);
                ((EventItemViewHolder) viewHolder).bind(event);
                break;
            default:
                // do nothing
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (events.size() == 0 || position == events.size()) {
            return ViewType.FOOTER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return events.size() + (isReachedLast ? 0 : 1);
    }
}

