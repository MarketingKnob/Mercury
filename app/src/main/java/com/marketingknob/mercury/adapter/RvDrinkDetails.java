package com.marketingknob.mercury.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marketingknob.mercury.R;
import com.marketingknob.mercury.model.CatProductsModel;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.BaseRatingBar;

import java.util.ArrayList;

public class RvDrinkDetails extends RecyclerView.Adapter<RvDrinkDetails.MyViewHolder> {

    private Context context;
    ArrayList<CatProductsModel> catProductsModelArrayList= new ArrayList<CatProductsModel>();

    public RvDrinkDetails(Context context, ArrayList<CatProductsModel> catProductsModelArrayList) {
        this.context = context;
        this.catProductsModelArrayList = catProductsModelArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat llDrink;
        private BaseRatingBar simpleRatingBar;

        private AppCompatImageView ivDrink;
        private AppCompatTextView tvDrinkName,tvDrinkPrice;

        public MyViewHolder(View view) {
            super(view);

            simpleRatingBar         = view.findViewById(R.id.simpleRatingBar);
            ivDrink                 = view.findViewById(R.id.iv_drink);
            tvDrinkName             = view.findViewById(R.id.tv_drink_name);
            tvDrinkPrice            = view.findViewById(R.id.tv_drink_price);
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

        holder.simpleRatingBar.setRating(Float.parseFloat(catProductsModelArrayList.get(position).getStrRating()));
        holder.tvDrinkName.setText(catProductsModelArrayList.get(position).getStrName());
        holder.tvDrinkPrice.setText(context.getResources().getString(R.string.price)+": "+catProductsModelArrayList.get(position).getStrPrice());

        Picasso.get()
                .load(catProductsModelArrayList.get(position).getStrProductUrl())
                .placeholder(R.drawable.bottal)
                .error(R.drawable.bottal)
                .into(holder.ivDrink);

    }

    @Override
    public int getItemCount() {
        return catProductsModelArrayList.size();
    }
}

