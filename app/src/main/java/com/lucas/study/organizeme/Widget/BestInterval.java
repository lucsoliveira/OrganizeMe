package com.lucas.study.organizeme.Widget;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.lucas.study.organizeme.Model.TimingModel;
import com.lucas.study.organizeme.R;
import com.lucas.study.organizeme.View.Message;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

public class BestInterval {

    private int msg;
    private CoordinatorLayout layoutAttack;
    public Message msgBestInterval;
    public BestInterval(){

    }

    public BestInterval(CoordinatorLayout layoutAttack){

        msgBestInterval = new Message(R.string.message_need_more_for_interval,
                MaterialDrawableBuilder.IconValue.TIMELAPSE,
                (CoordinatorLayout) layoutAttack.findViewById(R.id.messageBestInterval),
                R.color.colorPrimaryDark,
                96);

        this.layoutAttack = layoutAttack;

    }



    public CoordinatorLayout getLayoutAttack() {
        return layoutAttack;
    }

    public void setLayoutAttack(CoordinatorLayout layoutAttack) {
        this.layoutAttack = layoutAttack;
    }

    public void widgetShow(){

        msgBestInterval.setIconValue(MaterialDrawableBuilder.IconValue.TIMER);

        if(bestInterval() == "0"){
            msgBestInterval.showMessageViewWithString("Nao ha cronometragens suficientes. Continue usando o aplicativo.");
            msgBestInterval.showMessageView();
        }else {
            msgBestInterval.showMessageViewWithString("Com base em suas estatísticas, o intervalo do dia em que você é mais produtivo ocorre entre " + bestInterval());
        }

        getLayoutAttack().setVisibility(View.VISIBLE);
    }


    public void widgetHide(){
        getLayoutAttack().setVisibility(View.GONE);
    }

    private String bestInterval(){
        List<TimingModel> timesInterval = TimingModel.createTaskTimeProductivity("2","50");
        List<String> createdAt = new ArrayList<String>();
        List<String> finishedAt = new ArrayList<String>();
        List<Integer> arrayIntCreatedAt = new ArrayList<Integer>();

        for(int i = 0; i < timesInterval.size(); i++){
            createdAt.add(timesInterval.get(i).getStartAt());
            finishedAt.add(timesInterval.get(i).getCreatedAt());
        }

        for(int i = 0; i < createdAt.size(); i++){

        }
        //verification what interval
        if(timesInterval.size() != 0){
            return "19:00 às 20:00";
        }else{
            return "0";
        }

    }

}
