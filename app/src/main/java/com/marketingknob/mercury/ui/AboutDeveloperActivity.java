package com.marketingknob.mercury.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.marketingknob.mercury.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

/**
 * Created by Akshya on 12/11/2018.
 */
public class AboutDeveloperActivity extends AppCompatActivity {

    @BindView(R.id.tv_description) AppCompatTextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);

        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(AboutDeveloperActivity.this);

    }
}
