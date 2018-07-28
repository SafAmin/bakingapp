package com.baking.thebaking.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.baking.thebaking.R;

import butterknife.Unbinder;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static String SELECTED_RECIPE_PARAM = "SELECTED_RECIPE";

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private boolean isSaveInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        configureToolbar();
        initViews();
        progressDialog = new ProgressDialog(this);

        isSaveInstance = savedInstanceState != null;
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void initViews();

    private void configureToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setScreenTitle(String title) {
        toolbar.setTitle(title);
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void addToolbarNavigationListener() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void showProgressDialog() {
        if (!isSaveInstance) {
            progressDialog.setMessage(getString(R.string.recipes_loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
