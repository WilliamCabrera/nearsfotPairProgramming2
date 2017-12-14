package com.example.william.nearsoftpairprogramming2.di;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by William on 12/8/2017.
 */

@Module(includes = {ContextModule.class, GsonModule.class})
public class NetworkModule {

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache)
    {
        return new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    public  HttpLoggingInterceptor loggingInterceptor()
    {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String s) {

            }
        });
    }

    @Provides
    public Cache cache(File cachefile)
    {
        return new Cache(cachefile, 10*1000*1000); // 10MB cache
    }

    @Provides
    public File file(Context context)
    {
        return new File(context.getCacheDir(),"okhttp_cache");
    }



}
