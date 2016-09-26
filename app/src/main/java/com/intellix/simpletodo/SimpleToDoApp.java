package com.intellix.simpletodo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.facebook.stetho.Stetho;


/**
 * Created by intellix on 9/26/2016.
 */

public class SimpleToDoApp extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(TaskModel.class);
        ActiveAndroid.initialize(config.create());
        if (Cache.isInitialized() && Cache.getTableInfos().isEmpty()) {
            ActiveAndroid.dispose();
        }
        ActiveAndroid.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
