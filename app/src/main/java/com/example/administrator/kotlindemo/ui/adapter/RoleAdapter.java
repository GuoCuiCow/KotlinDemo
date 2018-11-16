package com.example.administrator.kotlindemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.kotlindemo.R;
import com.example.administrator.kotlindemo.data.entity.RoleBean;

import java.util.List;

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:角色的适配器
 */
public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.MyViewHolder> {
    private final Context mContext;
    private List<RoleBean> roles;
    private ClickListener mClickListener;
    RequestOptions options = new RequestOptions()
            .centerCrop()
            .error(R.mipmap.error)
            .priority(Priority.HIGH);
    public RoleAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
    }

    public void setRoles(List<RoleBean> roles) {
        this.roles = roles;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_role, viewGroup,
                false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv.setText(roles.get(position).name);
        Glide.with(mContext).load(roles.get(position).avatar).apply(options).into(holder.iv);
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(position);
                }
            }
        });//修改
        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onLongClick(position);
                }
                return false;
            }
        });//长按删除
    }

    @Override
    public int getItemCount() {
        return roles != null ? roles.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;
        LinearLayout content;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_role);
            tv = (TextView) view.findViewById(R.id.tv_name);
            content = (LinearLayout) view.findViewById(R.id.ll_content);
        }
    }

    public interface ClickListener {
        void onClick(int i);

        void onLongClick(int i);
    }

}
