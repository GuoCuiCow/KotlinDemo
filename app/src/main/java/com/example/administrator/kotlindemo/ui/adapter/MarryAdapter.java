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
import com.example.administrator.kotlindemo.data.entity.MarryBean;
import com.example.administrator.kotlindemo.data.entity.RoleBean;

import java.util.List;

/**
 * author: CuiGuo
 * date: 2018/11/15
 * info:匹配的适配器
 */
public class MarryAdapter extends RecyclerView.Adapter<MarryAdapter.MyViewHolder> {
    private final Context mContext;
    private List<MarryBean> roles;
    private ClickListener mClickListener;
    RequestOptions options = new RequestOptions()
            .centerCrop()
            .error(R.mipmap.error)
            .priority(Priority.HIGH);

    public MarryAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
    }

    public void setRoles(List<MarryBean> roles) {
        this.roles = roles;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_marry, viewGroup,
                false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.sendName.setText(roles.get(position).sender.name);
        Glide.with(mContext).load(roles.get(position).sender.avatar).apply(options).into(holder.sendAvatar);
        holder.receiverName.setText(roles.get(position).receiver.name);
        Glide.with(mContext).load(roles.get(position).receiver.avatar).apply(options).into(holder.receiverAvatar);
    }

    @Override
    public int getItemCount() {
        return roles != null ? roles.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView sendAvatar;
        TextView sendName;
        ImageView receiverAvatar;
        TextView receiverName;


        public MyViewHolder(View view) {
            super(view);
            sendAvatar = (ImageView) view.findViewById(R.id.iv_send_avatar);
            sendName = (TextView) view.findViewById(R.id.tv_send_name);
            receiverAvatar = (ImageView) view.findViewById(R.id.iv_re_avatar);
            receiverName = (TextView) view.findViewById(R.id.tv_re_name);

        }
    }

    public interface ClickListener {
        void onClick(int i);

        void onLongClick(int i);
    }

}
