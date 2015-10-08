package com.example.octodroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.octodroid.R;
import com.yatatsu.autobundle.Arg;
import com.yatatsu.autobundle.AutoBundle;

import butterknife.ButterKnife;

public class RepositoryNotificationListFragment extends Fragment {
    @Arg
    int repositoryId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoBundle.bind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_notification_list, null);
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
    }
}
