package com.example.william.nearsoftpairprogramming2.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by William on 12/8/2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context)
    {
        this.context = context;
    }


    @Provides
    public Context getcontext()
    {
      return  context ;
    }
}
