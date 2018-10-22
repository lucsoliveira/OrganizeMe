package com.lucas.study.organizeme.Widget;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;

import java.util.List;

public class BestChoiceWithHumor {

    private int msg;
    private CoordinatorLayout layoutAttack;

    public ImageView iconCardBest1, iconCardBest2, iconCardBest3;
    public TextView textCardBest1, textCardBest2, textCardBest3;

    public BestChoiceWithHumor(){

    }

    public BestChoiceWithHumor(CoordinatorLayout layoutAttack){
        /* CARD BEST CHOICE */
        textCardBest1 = (TextView) layoutAttack.findViewById(R.id.text1CardBestChoide);
        textCardBest2 = (TextView) layoutAttack.findViewById(R.id.text2CardBestChoide);
        textCardBest3 = (TextView) layoutAttack.findViewById(R.id.text3CardBestChoide);

        iconCardBest1 = (ImageView) layoutAttack.findViewById(R.id.icon1CadBestChoice);
        iconCardBest2 = (ImageView) layoutAttack.findViewById(R.id.icon2CardBestChoice);
        iconCardBest3 = (ImageView) layoutAttack.findViewById(R.id.icon3CardBestChoice);

        iconCardBest1.setImageResource(R.drawable.ic_happy);
        iconCardBest2.setImageResource(R.drawable.ic_neutral);
        iconCardBest3.setImageResource(R.drawable.ic_sad);

        this.layoutAttack = layoutAttack;

    }



    public CoordinatorLayout getLayoutAttack() {
        return layoutAttack;
    }

    public void setLayoutAttack(CoordinatorLayout layoutAttack) {
        this.layoutAttack = layoutAttack;
    }

    public void widgetShow(){
        int minutesHappy = bestProductivity(String.valueOf(2),null);
        int minutesNeutral = bestProductivity(String.valueOf(1),null);
        int minutesSad = bestProductivity(String.valueOf(0),null);

        if(minutesHappy != 0){ textCardBest1.setText(minutesHappy + " m."); }
        if(minutesNeutral != 0){ textCardBest2.setText(minutesNeutral + " m."); }
        if(minutesSad != 0){ textCardBest3.setText(minutesSad + " m."); }

        getLayoutAttack().setVisibility(View.VISIBLE);
    }


    public void widgetHide(){
        getLayoutAttack().setVisibility(View.GONE);
    }

    private int bestProductivity(String humor, Long task){

        if(task == null){
            List<TimingModel> timesProductivity = TimingModel.createTaskTimeProductivity("2",humor,"100");
            int sizeList = timesProductivity.size();

            if(sizeList == 0){
                return 0;
            }

            int secondsMedium = 0;

            for(int i = 0; i< sizeList; i++){
                secondsMedium += timesProductivity.get(i).getTimeSecondsTask();
            }

            return ((secondsMedium/sizeList)/60);
        }


        return 0;

    }

}
