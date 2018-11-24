package com.lucas.study.organizeme.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lucas.study.organizeme.R;

public class Default extends Dialog implements View.OnClickListener {

    public Context c;
    public TextView titleDialog, msgDialog;
    public String strTitle, strMsg;
    private CoordinatorLayout coordinatorLayout;

    public Default(Context a, String strTitle, String strMsg) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.strTitle = strTitle;
        this.strMsg = strMsg;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_default);

        titleDialog = (TextView) findViewById(R.id.txt_title_dialog);
        msgDialog = (TextView) findViewById(R.id.msg_dialog_default);

        titleDialog.setText(strTitle);
        msgDialog.setText(strMsg);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

        //dismiss();
    }
}
