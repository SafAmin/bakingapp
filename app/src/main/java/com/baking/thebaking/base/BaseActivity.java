package com.baking.thebaking.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baking.thebaking.R;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static String SELECTED_RECIPE_PARAM = "SELECTED_RECIPE";
    private ProgressDialog progressDialog;
    private boolean isSaveInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        bindViews();
        initViews();

        progressDialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            isSaveInstance = false;
        } else {
            isSaveInstance = true;
        }
    }

    public abstract void bindViews();

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void initViews();

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
