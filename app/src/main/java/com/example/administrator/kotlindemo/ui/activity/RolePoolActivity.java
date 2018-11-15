package com.example.administrator.kotlindemo.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.administrator.kotlindemo.R;
import com.example.administrator.kotlindemo.base.BaseActivity;
import com.example.administrator.kotlindemo.data.entity.RoleBean;
import com.example.administrator.kotlindemo.ui.adapter.RoleAdapter;
import com.example.administrator.kotlindemo.util.LocalJsonResolutionUtils;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:用户群列表
 */
public class RolePoolActivity extends BaseActivity {

    private List<RoleBean> roleBeans = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activtiy_role_pool;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        //初始化列表
        RecyclerView mRecyclerView = findViewById(R.id.rv_roles);
//设置布局管理器
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//设置adapter
        RoleAdapter adapter = new RoleAdapter(mRecyclerView);
        adapter.setRoles(roleBeans);
        mRecyclerView.setAdapter(adapter);
//设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView right=findViewById(R.id.tv_title_right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RolePoolActivity.this,AddRoleActivity.class));
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
//得到本地json文本内容
        String fileName = "roles.json";
        String foodJson = LocalJsonResolutionUtils.getJson(this, fileName);
//转换为对象
        roleBeans = LocalJsonResolutionUtils.JsonToArray(foodJson, new TypeToken<List<RoleBean>>() {
        }.getType());

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
