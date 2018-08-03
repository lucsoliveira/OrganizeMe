package com.lucas.study.organizeme.View;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.study.organizeme.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class Message {

    private int msg;
    private MaterialDrawableBuilder.IconValue iconValue;
    private CoordinatorLayout layoutAttack;
    private int colorIcon;
    private int sizeDpIcon;

    public Message(int msg, MaterialDrawableBuilder.IconValue iconValue, CoordinatorLayout layoutAttack, int colorIcon, int sizeDpIcon){

        this.msg = msg;
        this.iconValue = iconValue;
        this.layoutAttack = layoutAttack;
        this.colorIcon = colorIcon;
        this.sizeDpIcon = sizeDpIcon;

    }

    public Message(CoordinatorLayout layoutAttack){

        this.layoutAttack = layoutAttack;

    }



    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public MaterialDrawableBuilder.IconValue getIconValue() {
        return iconValue;
    }

    public void setIconValue(MaterialDrawableBuilder.IconValue iconValue) {
        this.iconValue = iconValue;
    }

    public CoordinatorLayout getLayoutAttack() {
        return layoutAttack;
    }

    public void setLayoutAttack(CoordinatorLayout layoutAttack) {
        this.layoutAttack = layoutAttack;
    }

    public int getColorIcon() {
        return colorIcon;
    }

    public void setColorIcon(int colorIcon) {
        this.colorIcon = colorIcon;
    }

    public int getSizeDpIcon() {
        return sizeDpIcon;
    }

    public void setSizeDpIcon(int sizeDpIcon) {
        this.sizeDpIcon = sizeDpIcon;
    }

    public void showMessageView(){


        TextView msgText = getLayoutAttack().findViewById(R.id.textMessageView);
        msgText.setText(getMsg());

        Drawable yourDrawable = MaterialDrawableBuilder.with(getLayoutAttack().getContext()) // provide a context
                .setIcon(getIconValue()) // provide an icon
                .setColor(getColorIcon()) // set the icon color
                .setSizeDp(getSizeDpIcon()) // set the icon size
                .build(); // Finally call build
        ImageView iconMessageView = getLayoutAttack().findViewById(R.id.iconMessageView);
        iconMessageView.setImageDrawable(yourDrawable);

        getLayoutAttack().setVisibility(View.VISIBLE);
    }

    public void showMessageViewWithString(String msg){


        TextView msgText = getLayoutAttack().findViewById(R.id.textMessageView);
        msgText.setText(msg);

        Drawable yourDrawable = MaterialDrawableBuilder.with(getLayoutAttack().getContext()) // provide a context
                .setIcon(getIconValue()) // provide an icon
                .setColor(getColorIcon()) // set the icon color
                .setSizeDp(getSizeDpIcon()) // set the icon size
                .build(); // Finally call build
        ImageView iconMessageView = getLayoutAttack().findViewById(R.id.iconMessageView);
        iconMessageView.setImageDrawable(yourDrawable);

        getLayoutAttack().setVisibility(View.VISIBLE);
    }


    public void hideMessageView(){
        getLayoutAttack().setVisibility(View.GONE);
    }

}
