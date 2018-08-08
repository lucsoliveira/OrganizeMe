package com.lucas.study.organizeme.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.lucas.study.organizeme.MainActivity;
import com.lucas.study.organizeme.Model.AppConfig;
import com.lucas.study.organizeme.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class FirstUse extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
      
        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.

        SliderPage slider0Page = new SliderPage();
        SliderPage slider1Page = new SliderPage();
        SliderPage slider2Page = new SliderPage();
        SliderPage slider3Page = new SliderPage();

        String headerWelcome = getResources().getString(R.string.message_welcome) + " " + getResources().getString(R.string.app_name);
        slider0Page.setTitle(headerWelcome);
        slider0Page.setDescription(getResources().getString(R.string.tab_description));
        slider0Page.setImageDrawable(R.drawable.ic_launcher);
        slider0Page.setBgColor(getResources().getColor(R.color.colorGreyDark));

        slider1Page.setTitle(getResources().getString(R.string.tab_one_title));
        slider1Page.setDescription(getResources().getString(R.string.tab_one_description));
        slider1Page.setImageDrawable(R.drawable.ic_baseline_show_chart_24px);
        slider1Page.setBgColor(getResources().getColor(R.color.colorGreyDark));

        slider2Page.setTitle(getResources().getString(R.string.tab_two_title));
        slider2Page.setImageDrawable(R.drawable.ic_baseline_sentiment_very_satisfied_24px);
        slider2Page.setDescription(getResources().getString(R.string.tab_two_description));
        slider2Page.setBgColor(getResources().getColor(R.color.colorPrimaryDark));

        slider3Page.setTitle(getResources().getString(R.string.tab_three_title));
        slider3Page.setImageDrawable(R.drawable.ic_baseline_playlist_add_check_24px);
        slider3Page.setDescription(getResources().getString(R.string.tab_three__description));
        slider3Page.setBgColor(getResources().getColor(R.color.colorBlueDark));

        addSlide(AppIntroFragment.newInstance(slider0Page));
        addSlide(AppIntroFragment.newInstance(slider1Page));
        addSlide(AppIntroFragment.newInstance(slider2Page));
        addSlide(AppIntroFragment.newInstance(slider3Page));


        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        getMainActivity();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        getMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    public void getMainActivity(){

        List<AppConfig> appConfig = AppConfig.creatListConfig();
        appConfig.get(0).setFirstUse(false);
        appConfig.get(0).save();

        Intent intentFirstUse = new Intent(this, MainActivity.class);
        startActivity(intentFirstUse);


    }
}