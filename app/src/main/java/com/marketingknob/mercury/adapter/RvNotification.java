package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.model.NotificationModel;
import com.marketingknob.mercury.ui.fragments.NotificationFragment;

import java.util.ArrayList;

public class RvNotification extends RecyclerView.Adapter<RvNotification.MyViewHolder> {

    NotificationFragment notificationFragment;
    ArrayList<NotificationModel> notificationModelArrayList;

    public RvNotification(NotificationFragment notificationFragment, ArrayList<NotificationModel> notificationModelArrayList) {
        this.notificationFragment = notificationFragment;
        this.notificationModelArrayList = notificationModelArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton btnReadUnread;
        AppCompatTextView tvNotification;
        LinearLayoutCompat llDrink;

        public MyViewHolder(View view) {
            super(view);

            btnReadUnread  = view.findViewById(R.id.btn_read_unread);
            tvNotification = view.findViewById(R.id.tv_notification);
        }
    }


    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_notification, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new  MyViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {

        holder.tvNotification.setText(notificationModelArrayList.get(position).getDescription());

        if (notificationModelArrayList.get(position).getStatus()){
            holder.btnReadUnread.setBackground(notificationFragment.getResources().getDrawable(R.drawable.red_background));
            holder.btnReadUnread.setText(notificationFragment.getResources().getString(R.string.read));
        }
        else {
            holder.btnReadUnread.setBackground(notificationFragment.getResources().getDrawable(R.drawable.green_background));
            holder.btnReadUnread.setText(notificationFragment.getResources().getString(R.string.unread));
        }

        if (!holder.btnReadUnread.getText().toString().equalsIgnoreCase("Read")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notificationFragment.readNotification(notificationModelArrayList.get(position).getId());
                }
            });
        }
        else {
            System.out.print("Read");
        }



    }

    @Override
    public int getItemCount() {
        return notificationModelArrayList.size();
    }
}


