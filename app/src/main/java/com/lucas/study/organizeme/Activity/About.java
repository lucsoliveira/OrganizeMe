package com.lucas.study.organizeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.lucas.study.organizeme.R;

import mehdi.sakout.aboutpage.AboutPage;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_happy)
                .addGroup("Contato")
                .addEmail("olucas1998@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("olucaoo")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("lucsoliveira")
                .addYoutube("UCu6PmVEyi_Ea_h9rk2RV7aA")
                .create();

        setContentView(aboutPage);
    }


}
