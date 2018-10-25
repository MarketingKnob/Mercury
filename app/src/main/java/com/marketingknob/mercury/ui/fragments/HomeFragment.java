package com.marketingknob.mercury.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.adapter.RvDrinkCategory;
import com.marketingknob.mercury.adapter.RvDrinkDetails;
import com.marketingknob.mercury.model.DrinkCategoryModel;
import com.marketingknob.mercury.util.CommonUtil;
import com.marketingknob.mercury.util.DialogUtil;
import com.marketingknob.mercury.util.FloatingButton;
import com.marketingknob.mercury.util.ProgressDialogUtil;
import com.marketingknob.mercury.util.SnackBarUtil;
import com.marketingknob.mercury.util.TinyDB;
import com.marketingknob.mercury.webservices.ApiHelper;
import com.marketingknob.mercury.webservices.WebConstants;
import com.marketingknob.mercury.webservices.interfaces.ApiResponseHelper;
import com.marketingknob.mercury.webservices.webresponse.BannerResponse;
import com.marketingknob.mercury.webservices.webresponse.DrinkCategoryResponse;
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

public class HomeFragment extends Fragment implements View.OnClickListener,ApiResponseHelper {

    Slider slider;
    RecyclerView rViewDrink,rViewDetails;
    SearchView searchView;
    AppCompatImageView ivLogOut;
    Context context;
    TinyDB tinyDB;
    LinearLayoutCompat llMain;

    RvDrinkCategory rvDrinkCategory;
    RvDrinkDetails rvDrinkDetails;
    ProgressDialog pd;

    List<Slide> slideList;
    ArrayList<DrinkCategoryModel> drinkCategoryModelArrayList;
    private static final String TAG = "HomeFragment";
    String strBannerUrl="",strBaseUrl="",strDrinkCateUrl="";

    public String strCategoryId="";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        return view;
    }

    void init(View view){

        context                         = this.getActivity();
        slideList                       = new ArrayList<>();
        drinkCategoryModelArrayList     = new ArrayList<DrinkCategoryModel>();
        slider                          = view.findViewById(R.id.slider);

        rViewDrink                      = view.findViewById(R.id.rv_drink_catg);
        rViewDetails                    = view.findViewById(R.id.rv_drink_detail);
        searchView                      = view.findViewById(R.id.search_view);
        ivLogOut                        = view.findViewById(R.id.iv_logout);
        llMain                          = view.findViewById(R.id.ll_main);

        tinyDB                          = new TinyDB(context);
        strBaseUrl                      = WebConstants.BASE_URL;

        ivLogOut.setOnClickListener(this);
        llMain.setOnClickListener(this);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.red));
        searchEditText.setHintTextColor(getResources().getColor(R.color.red));

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
        rvDrinkDetails = new RvDrinkDetails(context);
        rViewDetails.setAdapter(rvDrinkDetails);

        FloatingButton.floatingWorking(context,getActivity());

        pd = ProgressDialogUtil.getProgressDialogMsg(context, getResources().getString(R.string.fetch_details));
        pd.show();
        new ApiHelper().getBanner( HomeFragment.this);
        new ApiHelper().getDrinkCategory( HomeFragment.this);

    }

    @Override
    public void onClick(View view) {
        if (view==ivLogOut){
            DialogUtil.LogoutDialog(getActivity(),tinyDB);
        }else if (view==llMain){
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
        if(typeApi.equalsIgnoreCase("Banner")) {
            BannerResponse bannerResponse = new Gson().fromJson(response.body(), BannerResponse.class);
            if(bannerResponse != null) {
                if (!bannerResponse.getError()) {
                    for (int i = 0; i <bannerResponse.getImages().getBanner().size() ; i++) {

                        String strBannerPath = bannerResponse.getImages().getBanner().get(i);
                        strBannerUrl=strBaseUrl+strBannerPath;
                        Log.d(TAG, "onSuccess: "+strBannerUrl);

                        slideList.add(new Slide(i,strBannerUrl , 0));
                    }

                    /*Add Banner To the Slider*/
                    slider.addSlides(slideList);

                } else {
                    SnackBarUtil.showSnackBar(getActivity(),getResources().getString(R.string.error_try_again),llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(getActivity(),getResources().getString(R.string.error_try_again),llMain);
            }
        }
        else if(typeApi.equalsIgnoreCase("DrinkCategory")) {
            DrinkCategoryResponse drinkCategoryResponse = new Gson().fromJson(response.body(), DrinkCategoryResponse.class);
            if(drinkCategoryResponse != null) {
                if (!drinkCategoryResponse.getError()) {

                    strCategoryId = drinkCategoryResponse.getCategory().getResult().get(0).getId();
                    Log.d(TAG, "productDetail:Before "+strCategoryId);

                    for (int i = 0; i <drinkCategoryResponse.getCategory().getResult().size() ; i++) {


                        DrinkCategoryModel drinkCategoryModel = new DrinkCategoryModel();

                        String strDrinkCatePath = drinkCategoryResponse.getCategory().getResult().get(i).getIcon();
                        strDrinkCateUrl         = strBaseUrl+strDrinkCatePath;

                        drinkCategoryModel.setStrId(drinkCategoryResponse.getCategory().getResult().get(i).getId());
                        drinkCategoryModel.setStrName(drinkCategoryResponse.getCategory().getResult().get(i).getName());
                        drinkCategoryModel.setStrIcon(strDrinkCateUrl);

                        drinkCategoryModelArrayList.add(drinkCategoryModel);
                    }

                    rvDrinkCategory = new RvDrinkCategory(context,drinkCategoryModelArrayList,HomeFragment.this);
                    rViewDrink.setAdapter(rvDrinkCategory);

                } else {
                    SnackBarUtil.showSnackBar(getActivity(),drinkCategoryResponse.getMessage(),llMain);
                }
            } else {
                SnackBarUtil.showSnackBar(getActivity(),getResources().getString(R.string.error_try_again),llMain);
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



    public void productDetail(String strCategId){

        Log.d(TAG, "productDetail:After "+strCategId);


    }

}
