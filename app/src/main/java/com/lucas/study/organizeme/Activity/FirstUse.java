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
        //addSlide(TestSlide.newInstance(R.layout.fragment_intro2));

        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.

        SliderPage slider1Page = new SliderPage();
        SliderPage slider2Page = new SliderPage();
        SliderPage slider3Page = new SliderPage();


        slider1Page.setTitle("Otimize seu tempo");
        slider1Page.setDescription("Saiba qual a média de duração para obter uma ótima produtividade em todas as usas atividades.");
        slider1Page.setBgColor(getResources().getColor(R.color.colorPrimaryDark));

        slider2Page.setTitle("Saiba o que lhe incomoda");
        slider2Page.setDescription("Com base em estatísticas do seu Humor, você saberá qual atividade que mais consome-lhe energia.");
        slider2Page.setBgColor(getResources().getColor(R.color.colorPrimaryDark));

        slider3Page.setTitle("Solução para esquecimentos");
        slider3Page.setDescription("Crie uma lista de lembretes e tarefas a fazer e não esqueça de mais nada.");
        slider3Page.setBgColor(getResources().getColor(R.color.colorPrimaryDark));

        addSlide(AppIntroFragment.newInstance(slider1Page));
        addSlide(AppIntroFragment.newInstance(slider2Page));
        addSlide(AppIntroFragment.newInstance(slider3Page));


        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

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