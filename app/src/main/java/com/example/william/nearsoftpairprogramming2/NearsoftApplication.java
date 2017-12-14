package com.example.william.nearsoftpairprogramming2;

import android.app.Application;

import com.example.william.nearsoftpairprogramming2.di.ApplicationComponent;
import com.example.william.nearsoftpairprogramming2.di.ApplicationModule;
import com.example.william.nearsoftpairprogramming2.di.ContextModule;
import com.example.william.nearsoftpairprogramming2.di.DaggerApplicationComponent;


/**
 * Created by William on 12/13/2017.
 */

public class NearsoftApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
