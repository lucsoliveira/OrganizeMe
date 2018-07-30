package com.lucas.study.organizeme;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogEndActivity extends Dialog implements android.view.View.OnClickListener {

    public Context c;
    public Button yes;
    public Long idTask;
    public int secondsTask, optionProductivity, optionHumor;
    public EditText moreInformation;
    public RadioButton optionGood, optionMedium, optionBad, optionHappy, optionNeutral, optionSad;
    private CoordinatorLayout coordinatorLayout;
    public Calendar calendar;

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

            /* RADIO BUTTONS */
            optionGood = (RadioButton) findViewById(R.id.optionGood);
            optionMedium = (RadioButton) findViewById(R.id.optionMedium);
            optionBad = (RadioButton) findViewById(R.id.optionBad);

            optionHappy = (RadioButton) findViewById(R.id.optionHappy);
            optionNeutral = (RadioButton) findViewById(R.id.optionNeutral);
            optionSad = (RadioButton) findViewById(R.id.optionSad);

            moreInformation = (EditText)findViewById(R.id.moreInformation);

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:

                    /* RADIO BUTTONS */
                    if(optionGood.isChecked()){ optionProductivity = 2;}
                    else if(optionMedium.isChecked()){ optionProductivity = 1;}
                    else if(optionBad.isChecked()){ optionProductivity = 0;}

                    if(optionHappy.isChecked()){ optionHumor = 2;}
                    else if(optionNeutral.isChecked()){ optionHumor = 1;}
                    else if(optionSad.isChecked()){ optionHumor = 0;}
                    /* END RADIO BUTTONS */

                    //GET TIME NOW
                    Calendar c = Calendar.getInstance();
                    //System.out.println("Current time =&gt; "+c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();


                    TaskTimingModel timer = new TaskTimingModel(idTask,
                            secondsTask,
                            optionProductivity,
                            optionHumor,moreInformation.getText().toString(),
                            formattedDate
                    );
                    timer.save();


                    Toast.makeText(getContext(),"Cronometragem Armazenada",Toast.LENGTH_LONG).show();

                    dismiss();


                    break;
                default:
                    break;
            }

            //dismiss();
        }
}
