package com.cat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by lee on 2017/12/8.
 */

public class MainActivity extends AppCompatActivity {

    LoadingAadpter loadingAadpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingAadpter = LoadingHelper.INSTANCE.LoadingHelper(getSupportFragmentManager(), R.id.container, new Runnable() {
            @Override
            public void run() {
                loadingAadpter.hide();
            }
        });
        findViewById(R.id.tv_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showLoading();
                findViewById(R.id.tv_loading).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingAadpter.hide();
                    }
                }, 2000);
            }
        });

        findViewById(R.id.tv_dialog_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showDialogLoading();
                findViewById(R.id.tv_loading).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingAadpter.hide();
                    }
                }, 2000);
            }
        });

        findViewById(R.id.tv_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAadpter.showLoaderr();

            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)findViewById(R.id.parent)).removeAllViews();
                FrameLayout frameLayout = new FrameLayout(getBaseContext());
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                frameLayout.setId(R.id.container);
                ((ViewGroup)findViewById(R.id.parent)).addView(frameLayout);
                loadingAadpter = LoadingHelper.INSTANCE.LoadingHelper(getSupportFragmentManager(), R.id.container, new Runnable() {
                    @Override
                    public void run() {
                        loadingAadpter.hide();
                    }
                });
            }
        });

        findViewById(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)findViewById(R.id.parent)).removeAllViews();
            }
        });

    }


}