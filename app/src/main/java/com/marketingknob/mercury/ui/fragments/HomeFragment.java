package com.marketingknob.mercury.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.adapter.RvDrinkCategory;
import com.marketingknob.mercury.adapter.RvDrinkDetails;
import com.marketingknob.mercury.model.CatProductsModel;
import com.marketingknob.mercury.model.DrinkCategoryModel;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.FloatingButton;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.WebConstants;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.BannerResponse;
import com.marketingknob.mercury.webservices.webresponse.DrinkCategoryResponse;
import com.marketingknob.mercury.webservices.webresponse.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Response;

import static com.marketingknob.mercury.util.FloatingButton.leftCenterButton;
import static com.marketingknob.mercury.util.FloatingButton.rightLowerMenu;

/**
 * Created by Akshya on 23/10/2018.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponseHelper {

    Slider slider;
    RecyclerView rViewDrink, rViewDetails;
    Context context;
    LinearLayoutCompat llMain;

    RvDrinkCategory rvDrinkCategory;
    RvDrinkDetails rvDrinkDetails;
    ProgressDialog pd;

    List<Slide> slideList;
    ArrayList<DrinkCategoryModel> drinkCategoryModelArrayList;
    ArrayList<CatProductsModel> catProductsModelArrayList;
    private static final String TAG = "HomeFragment";
    String strBannerUrl = "", strImageBaseUrl = "", strDrinkCateUrl = "",strCatProductUrl="";

    public String strCategoryId = "";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        return view;
    }

    void init(View view) {

        context                         = this.getActivity();
        slideList                       = new ArrayList<>();
        drinkCategoryModelArrayList     = new ArrayList<DrinkCategoryModel>();

        slider                          = view.findViewById(R.id.slider);

        rViewDrink                      = view.findViewById(R.id.rv_drink_catg);
        rViewDetails                    = view.findViewById(R.id.rv_drink_detail);
        llMain                          = view.findViewById(R.id.ll_main);

        strImageBaseUrl                 = WebConstants.IMAGES_BASE_URL;

        llMain.setOnClickListener(this);

        /*Drink Category RecyclerView Configure*/
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rViewDrink.setLayoutManager(layoutManager);
        rViewDrink.setItemAnimator(new DefaultItemAnimator());

        /*Drink Details RecyclerView Configure*/
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rViewDetails.setLayoutManager(layoutManager1);
        rViewDetails.setItemAnimator(new DefaultItemAnimator());

        FloatingButton.floatingWorking(context, getActivity());

        pd = ProgressDialogUtil.getProgressDialogMsg(context, getResources().getString(R.string.fetch_details));
        pd.show();

        new ApiHelper().getBanner(HomeFragment.this);
        new ApiHelper().getDrinkCategory(HomeFragment.this);


    }

    @Override
    public void onClick(View view) {
        if (view == llMain) {
            CommonUtil.hideKeyboard(getActivity());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        leftCenterButton.detach();
        rightLowerMenu.close(false);
    }

    @Override
    public void onSuccess(Response<JsonElement> response, String typeApi) {
        dismissDialog();
        if (typeApi.equalsIgnoreCase("Banner")) {
            BannerResponse bannerResponse = new Gson().fromJson(response.body(), BannerResponse.class);
            if (bannerResponse != null) {
                if (!bannerResponse.getError()) {
                    for (int i = 0; i < bannerResponse.getImages().getBanner().size(); i++) {

                        String strBannerPath = bannerResponse.getImages().getBanner().get(i);
                        strBannerUrl = strImageBaseUrl + strBannerPath;
                        Log.d(TAG, "onSuccess: " + strBannerUrl);

                        slideList.add(new Slide(i, strBannerUrl, 0));
                    }

                    /*Add Banner To the Slider*/
                    slider.addSlides(slideList);

                } else {
                    SnackBarUtil.showSnackBar(getActivity(), getResources().getString(R.string.error_try_again), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(getActivity(), getResources().getString(R.string.error_try_again), llMain);
            }
        } else if (typeApi.equalsIgnoreCase("DrinkCategory")) {
            DrinkCategoryResponse drinkCategoryResponse = new Gson().fromJson(response.body(), DrinkCategoryResponse.class);
            if (drinkCategoryResponse != null) {
                if (!drinkCategoryResponse.getError()) {
                    strCategoryId = drinkCategoryResponse.getCategory().getResult().get(0).getId();
                    Log.d(TAG, "productDetail:Before " + strCategoryId);
                    for (int i = 0; i < drinkCategoryResponse.getCategory().getResult().size(); i++) {

                        DrinkCategoryModel drinkCategoryModel = new DrinkCategoryModel();
                        String strDrinkCatePath = drinkCategoryResponse.getCategory().getResult().get(i).getIcon();
                        strDrinkCateUrl = strImageBaseUrl + strDrinkCatePath;

                        drinkCategoryModel.setStrId(drinkCategoryResponse.getCategory().getResult().get(i).getId());
                        drinkCategoryModel.setStrName(drinkCategoryResponse.getCategory().getResult().get(i).getName());
                        drinkCategoryModel.setStrIcon(strDrinkCateUrl);

                        drinkCategoryModelArrayList.add(drinkCategoryModel);
                    }

                    rvDrinkCategory = new RvDrinkCategory(context, drinkCategoryModelArrayList, HomeFragment.this);
                    rViewDrink.setAdapter(rvDrinkCategory);

                    new ApiHelper().getCatProducts(HomeFragment.this, strCategoryId);

                } else {
                    SnackBarUtil.showSnackBar(getActivity(), drinkCategoryResponse.getMessage(), llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(getActivity(), getResources().getString(R.string.error_try_again), llMain);
            }
        } else if (typeApi.equalsIgnoreCase("CategoryProducts")) {

            ProductResponse productResponse = new Gson().fromJson(response.body(), ProductResponse.class);
            if (productResponse != null) {
                if (!productResponse.getError()) {

                    catProductsModelArrayList       = new ArrayList<CatProductsModel>();

                    for (int i = 0; i <productResponse.getProduct().getResult().size() ; i++) {

                        CatProductsModel catProductsModel = new CatProductsModel();

                        String strProductPath = productResponse.getProduct().getResult().get(i).getImage();
                        strCatProductUrl = strImageBaseUrl + strProductPath;

                        catProductsModel.setStrId(productResponse.getProduct().getResult().get(i).getId());
                        catProductsModel.setStrName(productResponse.getProduct().getResult().get(i).getName());
                        catProductsModel.setStrPrice(productResponse.getProduct().getResult().get(i).getPrice());
                        catProductsModel.setStrRating(productResponse.getProduct().getResult().get(i).getRating());
                        catProductsModel.setStrProductUrl(strCatProductUrl);

                        catProductsModelArrayList.add(catProductsModel);
                    }

                    rvDrinkDetails = new RvDrinkDetails(context,catProductsModelArrayList);
                    rViewDetails.setAdapter(rvDrinkDetails);

                }else {
                SnackBarUtil.showSnackBar(getActivity(), productResponse.getMessage(), llMain);
            }
        } else {
            SnackBarUtil.showSnackBar(getActivity(), getResources().getString(R.string.error_try_again), llMain);
        }
    }
}

    @Override
    public void onFailure(String error) {
        dismissDialog();
        DialogUtil.showDialogMsg(context, "Server Error", getResources().getString(R.string.server_error_try_again));
    }

    private void dismissDialog() {
        try {
            if (pd != null) {
                if (pd.isShowing())
                    pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void productDetail(String strCategId) {

        Log.d(TAG, "productDetail:After " + strCategId);
        pd = ProgressDialogUtil.getProgressDialogMsg(context, getResources().getString(R.string.product_details));
        pd.show();
        new ApiHelper().getCatProducts(HomeFragment.this, strCategId);

    }
}
