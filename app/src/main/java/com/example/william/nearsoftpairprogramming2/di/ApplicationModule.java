package com.example.william.nearsoftpairprogramming2.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.example.william.nearsoftpairprogramming2.NearsoftApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by William on 12/11/2017.
 */


@Module(includes = ContextModule.class)
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(NearsoftApplication nearsoftApplication)
    {
        application = nearsoftApplication;
    }

    @Provides
    @Singleton
    Application provideApplication()
    {
        return application;
    }

    @Provides
    @Singleton
        // Application reference must come from ApplicationModule.class
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences("nearsoft", Context.MODE_PRIVATE);
    }
}
