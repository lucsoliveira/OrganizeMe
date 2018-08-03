package com.lucas.study.organizeme.Model;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.List;

public class AppConfig extends SugarRecord<AppConfig> {

    private boolean isFirstUse;

    public boolean isFirstUse() {
        return isFirstUse;
    }

    public void setFirstUse(boolean firstUse) {
        isFirstUse = firstUse;
    }

    public AppConfig(boolean isFirstUse) {
        this.isFirstUse = isFirstUse;
    }

    public AppConfig(){
    }


    public static List<AppConfig> creatListConfig() {

        List<AppConfig> listAppConfig = AppConfig.findWithQuery(AppConfig.class,"Select * from App_Config");
        return listAppConfig;
    }

}
