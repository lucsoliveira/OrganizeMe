package com.lucas.study.organizeme.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lucas.study.organizeme.MainActivity;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.Model.TimingModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EndActivity extends Dialog implements View.OnClickListener {

    public Context c;
    public Button yes;
    public Long idTask;
    public int secondsTask, optionProductivity, optionHumor, optionHumorBefore;
    public EditText moreInformation;
    public RadioButton optionGood, optionMedium, optionBad,
            optionHappyBefore, optionNeutralBefore, optionSadBefore,
            optionHappy, optionNeutral, optionSad;
    private CoordinatorLayout coordinatorLayout;
    public Calendar calendar;
    final String startedAt;

    public EndActivity(Context a, Long idTask, int secondsTask, String startedAt) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.idTask = idTask;
            this.secondsTask = secondsTask;
            this.startedAt = startedAt;
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

            /* BEFORE ACTIVITY */

            optionHappyBefore = (RadioButton) findViewById(R.id.optionHappyBefore);
            optionNeutralBefore = (RadioButton) findViewById(R.id.optionNeutralBefore);
            optionSadBefore = (RadioButton) findViewById(R.id.optionSadBefore);


            /* END BEFOR */

            /* AFTER ACTIVITY */
            optionHappy = (RadioButton) findViewById(R.id.optionHappy);
            optionNeutral = (RadioButton) findViewById(R.id.optionNeutral);
            optionSad = (RadioButton) findViewById(R.id.optionSad);
            /* END AFTER */


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

                    if(optionHappyBefore.isChecked()){ optionHumorBefore = 2;}
                    else if(optionNeutralBefore.isChecked()){ optionHumorBefore = 1;}
                    else if(optionSadBefore.isChecked()){ optionHumorBefore = 0;}
                    /* END RADIO BUTTONS */

                    //GET TIME NOW
                    Calendar c = Calendar.getInstance();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    DateTime dt = formatter.parseDateTime(formattedDate);



                    //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();


                    TimingModel timer = new TimingModel(idTask,
                            secondsTask,
                            optionProductivity,
                            optionHumorBefore,
                            optionHumor,
                            moreInformation.getText().toString(),
                            dt.toString(),
                            startedAt,
                            dt.toString()
                    );
                    timer.save();


                    Toast.makeText(getContext(),"Cronometragem Armazenada",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getContext(), MainActivity.class);

                    getContext().startActivity(i);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                    notificationManager.cancelAll();

                    dismiss();


                    break;
                default:
                    break;
            }

            //dismiss();
        }
}
