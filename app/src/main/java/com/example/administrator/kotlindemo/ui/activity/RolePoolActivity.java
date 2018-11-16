package com.example.administrator.kotlindemo.ui.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.administrator.kotlindemo.R;
import com.example.administrator.kotlindemo.base.BaseActivity;
import com.example.administrator.kotlindemo.data.entity.MarryBean;
import com.example.administrator.kotlindemo.data.entity.RoleBean;
import com.example.administrator.kotlindemo.ui.adapter.RoleAdapter;
import com.example.administrator.kotlindemo.util.ACache;
import com.google.gson.Gson;
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
    private RoleAdapter adapter;
    private Gson gson;
    private ACache mCache;

    @Override
    public int getLayoutId() {
        return R.layout.activtiy_role_pool;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        TextView title = findViewById(R.id.tv_title);
        title.setText("英雄池");
        //初始化列表
        RecyclerView mRecyclerView = findViewById(R.id.rv_roles);
//设置布局管理器
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//设置adapter
        adapter = new RoleAdapter(mRecyclerView);
        adapter.setRoles(roleBeans);
        adapter.setClickListener(new RoleAdapter.ClickListener() {
            @Override
            public void onClick(int i) {
                RoleBean roleBean = roleBeans.get(i);

                Intent intent = new Intent(RolePoolActivity.this, AddRoleActivity.class);
                intent.putExtra("role", roleBean);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(final int i) {
                RoleBean roleBean = roleBeans.get(i);
                //长按删除
                Dialog dialog = new AlertDialog.Builder(RolePoolActivity.this)
                        .setMessage("是否删除" + roleBean.name)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                roleBeans.remove(i);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                dialog.show();
            }
        });
        mRecyclerView.setAdapter(adapter);
//设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView right = findViewById(R.id.tv_title_right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RolePoolActivity.this, AddRoleActivity.class), 1);
            }
        });

        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
        mCache = ACache.get(this);
        String value = mCache.getAsString("role_list");
        gson = new Gson();

        List<RoleBean> roles = gson.fromJson(value, new TypeToken<List<RoleBean>>() {
        }.getType());
        if (roles != null) {
            roleBeans.clear();
            roleBeans.addAll(roles);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }

    @Override
    protected void onDestroy() {
        String saveJson = gson.toJson(roleBeans);
        mCache.put("role_list", saveJson);
        super.onDestroy();
    }
}
