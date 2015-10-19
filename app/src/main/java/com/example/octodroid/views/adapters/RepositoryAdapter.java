package com.example.octodroid.views.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.octodroid.data.GitHub;
import com.example.octodroid.data.prefs.OctodroidPrefs;
import com.example.octodroid.data.prefs.OctodroidPrefsSchema;
import com.example.octodroid.views.components.DividerItemDecoration;
import com.example.octodroid.views.components.LinearLayoutLoadMoreListener;
import com.example.octodroid.views.helpers.ToastHelper;
import com.example.octodroid.views.holders.ProgressViewHolder;
import com.example.octodroid.views.holders.SelectableRepositoryItemViewHolder;
import com.jakewharton.rxbinding.view.RxView;
import com.rejasupotaro.octodroid.http.Params;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static class ViewType {
        private static final int ITEM = 1;
        private static final int FOOTER = 2;
    }

    private Context context;
    private RecyclerView recyclerView;
    private List<Repository> repositories = new ArrayList<>();
    private BehaviorSubject<Observable<Response<List<Repository>>>> responseSubject;
    private Observable<Response<List<Repository>>> pagedResponse;
    private boolean isReachedLast;

    private OctodroidPrefs octodroidPrefs;
    private Map<Integer, Repository> selectedRepositories = new HashMap<>();

    public RepositoryAdapter(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();
        this.recyclerView = recyclerView;

        octodroidPrefs = OctodroidPrefsSchema.get(context);
        for (String serializedRepositories : octodroidPrefs.getSeletedSerializedRepositories()) {
            Repository repository = Resource.fromJson(serializedRepositories, Repository.class);
            selectedRepositories.put(repository.getId(), repository);
        }

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

        requestUserRepositories();
    }

    private void requestUserRepositories() {
        Params params = new Params();
        params.add("per_page", "100");
        params.add("sort", "updated");
        responseSubject = BehaviorSubject.create(GitHub.client().userRepositories(params));
        responseSubject.takeUntil(RxView.detaches(recyclerView))
                .flatMap(r -> r)
                .subscribe(new ResponseSubscriber());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.FOOTER) {
            return ProgressViewHolder.create(parent);
        } else {
            return SelectableRepositoryItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ViewType.FOOTER:
                // do nothing
                break;
            default:
                Repository repository = repositories.get(position);
                ((SelectableRepositoryItemViewHolder) viewHolder).bind(
                        repository,
                        selectedRepositories.containsKey(repository.getId()),
                        new SelectableRepositoryItemViewHolder.OnSelectRepositoryListener() {
                            @Override
                            public void onSelect(int id) {
                                selectedRepositories.put(id, repository);
                            }

                            @Override
                            public void onUnSelect(int id) {
                                selectedRepositories.remove(id);
                            }
                        });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (repositories.size() == 0 || position == repositories.size()) {
            return ViewType.FOOTER;
        } else {
            return ViewType.ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size() + (isReachedLast ? 0 : 1);
    }

    private class ResponseSubscriber extends Subscriber<Response<List<Repository>>> {

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
        public void onNext(Response<List<Repository>> r) {
            if (r.entity().isEmpty()) {
                isReachedLast = true;
                notifyDataSetChanged();
                return;
            }

            List<Repository> items = r.entity();
            int startPosition = repositories.size();
            repositories.addAll(items);

            if (startPosition == 0) {
                notifyDataSetChanged();
            } else {
                notifyItemRangeInserted(startPosition, items.size());
            }

            pagedResponse = r.next();
        }
    }

    public void onDestroy() {
        Set<String> selectedSerializedRepositories = new HashSet<>();
        for (Integer repositoryId : selectedRepositories.keySet()) {
            Repository repository = selectedRepositories.get(repositoryId);
            selectedSerializedRepositories.add(repository.toJson());
        }
        octodroidPrefs.putSeletedSerializedRepositories(selectedSerializedRepositories);
    }
}

