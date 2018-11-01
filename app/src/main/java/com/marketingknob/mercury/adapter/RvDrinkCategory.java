package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.model.DrinkCategoryModel;
import com.marketingknob.mercury.ui.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akshya on 23/10/2018.
 */
public class RvDrinkCategory extends RecyclerView.Adapter<RvDrinkCategory.MyViewHolder> {

    private Context context;
    private ArrayList<DrinkCategoryModel> drinkCategoryModelArrayList = new ArrayList<>();
    private HomeFragment homeFragment;

    public RvDrinkCategory(Context context, ArrayList<DrinkCategoryModel> drinkCategoryModelArrayList,HomeFragment homeFragment) {
        this.context = context;
        this.drinkCategoryModelArrayList = drinkCategoryModelArrayList;
        this.homeFragment=homeFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivDrink;
        AppCompatTextView  tvDrink;

        public MyViewHolder(View view) {
            super(view);

            ivDrink     = view.findViewById(R.id.iv_drink_icon);
            tvDrink     = view.findViewById(R.id.tv_drink_name);
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

        holder.tvDrink.setText(drinkCategoryModelArrayList.get(position).getStrName());
        Picasso.get()
                .load(drinkCategoryModelArrayList.get(position).getStrIcon())
                .placeholder(R.drawable.wine_colord)
                .error(R.drawable.wine_colord)
                .into(holder.ivDrink);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeFragment.productDetail(drinkCategoryModelArrayList.get(position).getStrId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return drinkCategoryModelArrayList.size();
    }
}
