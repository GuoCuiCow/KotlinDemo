package com.example.administrator.kotlindemo.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kotlindemo.R;
import com.example.administrator.kotlindemo.base.BaseActivity;
import com.example.administrator.kotlindemo.data.entity.RoleBean;
import com.example.administrator.kotlindemo.util.LocalJsonResolutionUtils;
import com.example.administrator.kotlindemo.util.PhoneUtils;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.kotlindemo.util.PhoneUtils.PHONE_CODE_SHEAR_PACK;
import static com.example.administrator.kotlindemo.util.PhoneUtils.PHONE_CODE__CUT;
import static com.example.administrator.kotlindemo.util.PhoneUtils.PHONE_CODE__SHEAR_MYPHONE;

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:增加，修改
 */
public class AddRoleActivity extends BaseActivity {

    private List<RoleBean> roleBeans = new ArrayList<>();
    private ImageView avatar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_role;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        avatar = findViewById(R.id.iv_role);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPic();
            }
        });
        EditText name = findViewById(R.id.et_name);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHONE_CODE_SHEAR_PACK://拍照返回
                Uri packUri = PhoneUtils.onInitCutPhoneResult(this, requestCode, resultCode, data, "cut.jpg");
                PhoneUtils.crop(this, packUri);
                break;
            case PHONE_CODE__CUT://裁剪返回
                if (data != null) {
                    Bitmap photo = data.getParcelableExtra("data");
                    Glide.with(AddRoleActivity.this).load(photo).into(avatar);
                }
                break;
            case PHONE_CODE__SHEAR_MYPHONE://相册返回
                Uri myUri = PhoneUtils.onCutmyPhoneResult(this, requestCode, resultCode, data);
                PhoneUtils.crop(this, myUri);
                break;
        }

    }

    private static final int CAMERA_OK = 1;

    /**
     * 获取权限
     */
    private void getPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_OK);
            } else {
                //说明已经获取到摄像头权限了
                PhoneUtils.getSheraPack(AddRoleActivity.this, "cut.jpg");
            }
        } else {
//这个说明系统版本在6.0之下，不需要动态获取权限。

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_OK:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //takePhoto();
                    PhoneUtils.getSheraPack(AddRoleActivity.this, "cut.jpg");
                }
                break;
        }
    }


    //选择图片
    private void showPic() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwin_vip_pic, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window win = alertDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.BOTTOM);
        // lp.alpha = 0.7f;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        win.setAttributes(lp);
        win.setContentView(view);

        TextView tv_open_pic = (TextView) view.findViewById(R.id.tv_open_pic);
        TextView tv_open_cama = (TextView) view.findViewById(R.id.tv_open_cama);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);


        //打开相册
        tv_open_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                PhoneUtils.getSheraMyPhone(AddRoleActivity.this);
            }
        });
        //打开相机
        tv_open_cama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getPermission();
            }
        });
        //取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }
}
