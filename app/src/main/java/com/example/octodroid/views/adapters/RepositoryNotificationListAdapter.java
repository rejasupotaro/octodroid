package com.example.octodroid.views.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.data.GitHub;
import com.example.octodroid.views.components.LinearLayoutLoadMoreListener;
import com.example.octodroid.views.helpers.ToastHelper;
import com.example.octodroid.views.holders.NotificationItemViewHolder;
import com.example.octodroid.views.holders.ProgressViewHolder;
import com.jakewharton.rxbinding.view.RxView;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Notification;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;

public class RepositoryNotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static class ViewType {
        private static final int ITEM = 1;
        private static final int FOOTER = 2;
    }

    private Context context;
    private RecyclerView recyclerView;
    private List<Notification> notifications = new ArrayList<>();
    private BehaviorSubject<Observable<Response<List<Notification>>>> responseSubject;
    private Observable<Response<List<Notification>>> pagedResponse;
    private boolean isReachedLast;

    public RepositoryNotificationListAdapter(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();
        this.recyclerView = recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(null);
        recyclerView.addOnScrollListener(new LinearLayoutLoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (pagedResponse != null) {
                    responseSubject.onNext(pagedResponse);
                }
            }
        });

        requestUserRepositories();
    }

    private void requestUserRepositories() {
        responseSubject = BehaviorSubject.create(GitHub.client().notifications());
        responseSubject.takeUntil(RxView.detaches(recyclerView))
                .flatMap(r -> r)
                .subscribe(new ResponseSubscriber());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.FOOTER) {
            return ProgressViewHolder.create(parent);
        } else {
            return NotificationItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ViewType.FOOTER:
                // do nothing
                break;
            default:
                Notification notification = notifications.get(position);
                ((NotificationItemViewHolder) viewHolder).bind(notification);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (notifications.size() == 0 || position == notifications.size()) {
            return ViewType.FOOTER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size() + (isReachedLast ? 0 : 1);
    }

    private class ResponseSubscriber extends Subscriber<Response<List<Notification>>> {

        @Override
        public void onCompleted() {
            // do nothing
        }

        @Override
        public void onError(Throwable e) {
            isReachedLast = true;
            notifyDataSetChanged();
            ToastHelper.showError(context);
        }

        @Override
        public void onNext(Response<List<Notification>> r) {
            if (r.entity().isEmpty()) {
                isReachedLast = true;
                notifyDataSetChanged();
                return;
            }

            List<Notification> items = r.entity();
            int startPosition = notifications.size();
            notifications.addAll(items);

            if (startPosition == 0) {
                notifyDataSetChanged();
            } else {
                notifyItemRangeInserted(startPosition, items.size());
            }

            pagedResponse = r.next();
        }
    }
}

