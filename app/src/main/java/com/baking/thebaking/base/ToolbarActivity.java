package com.baking.thebaking.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.baking.thebaking.R;

import butterknife.ButterKnife;

public abstract class ToolbarActivity extends BaseActivity {

    @Nullable
    Toolbar toolbar;
    @Nullable
    FrameLayout layoutToolbarContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutToolbarContentContainer = findViewById(R.id.layout_toolbar_content_container);
        if (layoutToolbarContentContainer instanceof ViewGroup) {
            (layoutToolbarContentContainer)
                    .addView(LayoutInflater.from(this)
                            .inflate(getToolbarLayoutResource()
                                    , layoutToolbarContentContainer, false));
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toobar;
    }

    @Override
    public void initViews() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

   /* public void setScreenTitle(String title) {
        toolbar.setTitle(title);
    }*/

    protected abstract int getToolbarLayoutResource();

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    private void addToolbarNavigationListener() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
