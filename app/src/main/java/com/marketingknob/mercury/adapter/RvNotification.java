package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marketingknob.mercury.R;

public class RvNotification extends RecyclerView.Adapter<RvNotification.MyViewHolder> {

    private Context context;

    public RvNotification(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton btnReadUnread;

        LinearLayoutCompat llDrink;

        public MyViewHolder(View view) {
            super(view);

            btnReadUnread = view.findViewById(R.id.btn_read_unread);
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

        if (position==2 || position==4) {

            holder.btnReadUnread.setBackground(context.getResources().getDrawable(R.drawable.green_background));
            holder.btnReadUnread.setText(context.getResources().getString(R.string.unread));
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}


