package com.lucas.study.organizeme.Model;

import com.orm.SugarRecord;

public class AppConfig extends SugarRecord<AppConfig> {
    public boolean isFirstUse;

    public AppConfig(boolean isFirstUse) {
        this.isFirstUse = isFirstUse;
    }

}
