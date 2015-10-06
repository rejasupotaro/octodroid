package com.example.octodroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.octodroid.R;

public class RepositoryListActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RepositoryListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
    }
}
