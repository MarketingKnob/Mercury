package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marketingknob.mercury.R;

public class RvDrinkDetails extends RecyclerView.Adapter<RvDrinkDetails.MyViewHolder> {

    private Context context;

    public RvDrinkDetails(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat llDrink;


        public MyViewHolder(View view) {
            super(view);

        }
    }


    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_drink_details, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new  MyViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

