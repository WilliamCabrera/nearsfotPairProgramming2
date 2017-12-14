package com.example.william.nearsoftpairprogramming2.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

/**
 * Created by William on 12/8/2017.
 */
@Module
public class GsonModule {

    @Provides
    public Gson getGson()
    {
       return new GsonBuilder()
               .setLenient()
               .create();
    }
}
