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
import android.widget.EditText;
import android.widget.Toast;

public class DialogEndActivity extends Dialog implements android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button yes;
    public Long idTask;
    public int secondsTask;
    public EditText moreInformation;

    private CoordinatorLayout coordinatorLayout;

    public DialogEndActivity(Context a, Long idTask, int secondsTask) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.idTask = idTask;
            this.secondsTask = secondsTask;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_end_activity);
            yes = (Button) findViewById(R.id.btn_yes);
            yes.setOnClickListener(this);



            moreInformation = (EditText)findViewById(R.id.moreInformation);

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:

                    TasktTimingModel timer = new TasktTimingModel(idTask,secondsTask,1,2,moreInformation.getText().toString());
                    timer.save();


                    Toast.makeText(c,"Cronometragem Armazenada",Toast.LENGTH_LONG).show();

                    dismiss();


                    break;
                default:
                    break;
            }

            //dismiss();
        }
}
