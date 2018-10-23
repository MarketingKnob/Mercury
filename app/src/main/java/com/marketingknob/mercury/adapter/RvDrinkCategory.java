package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marketingknob.mercury.R;

import java.util.ArrayList;

/**
 * Created by Akshya on 23/10/2018.
 */

public class RvDrinkCategory extends RecyclerView.Adapter<RvDrinkCategory.MyViewHolder> {

    private Context context;

    public RvDrinkCategory(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat llDrink;


        public MyViewHolder(View view) {
            super(view);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_drink_category, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new MyViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
