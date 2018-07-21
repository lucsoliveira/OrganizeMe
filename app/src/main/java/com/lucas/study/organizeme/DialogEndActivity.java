package com.lucas.study.organizeme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class DialogEndActivity extends Dialog implements android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button yes;
    public Long idTime;

    private CoordinatorLayout coordinatorLayout;

    public DialogEndActivity(Context a, Long idTime) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.idTime = idTime;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_end_activity);
            yes = (Button) findViewById(R.id.btn_yes);
            yes.setOnClickListener(this);

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:

                    Toast.makeText(c,"Cronometragem Armazenada",Toast.LENGTH_LONG).show();

                    dismiss();


                    break;
                default:
                    break;
            }

            //dismiss();
        }
}
