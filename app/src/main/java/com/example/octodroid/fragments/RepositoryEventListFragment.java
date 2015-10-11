package com.example.octodroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.octodroid.R;
import com.example.octodroid.views.adapters.RepositoryEventListAdapter;
import com.yatatsu.autobundle.Arg;
import com.yatatsu.autobundle.AutoBundle;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryEventListFragment extends Fragment {
    @Bind(R.id.repository_event_list)
    RecyclerView repositoryEventListView;

    @Arg
    int repositoryId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoBundle.bind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_event_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViews();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void setupViews() {
        RepositoryEventListAdapter repositoryEventListAdapter = new RepositoryEventListAdapter(repositoryEventListView);
        repositoryEventListView.setAdapter(repositoryEventListAdapter);
    }
}
