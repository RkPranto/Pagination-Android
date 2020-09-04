package com.rezwan_cs.paginationandroid;

import android.os.Build;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    RecyclerView recyclerView;
    RecyclerViewModel viewModel;
    RecyclerAdapter adapter;
    ProgressBar progressBar ;
    LinearLayoutManager layoutManager;
    int totalItem  = 0, currentItems  = 0, scrolledOutItems  = 0;
    boolean isScrolling = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);
        viewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        adapter = new RecyclerAdapter();
        
        viewModel.setUpViewListener(this);
        viewModel.fetchNewData();
        viewModel.getObjectList().observe(this, new Observer<List<ObjectClass>>() {
            @Override
            public void onChanged(List<ObjectClass> objectClassList) {
                if(objectClassList != null){
                    adapter.appendObjectClasses(objectClassList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                    viewModel.fetchNewData();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrolledOutItems = layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems)>=totalItem){
                    isScrolling = false;

                }
            }
        });
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

}