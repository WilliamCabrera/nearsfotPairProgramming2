package com.example.william.nearsoftpairprogramming2.di;


import com.example.william.nearsoftpairprogramming2.retrofit.BookRetrofitApi;
import com.example.william.nearsoftpairprogramming2.retrofit.BookRetrofitApi2;
import com.example.william.nearsoftpairprogramming2.util.UrlProvider;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by William on 12/13/2017.
 */

@Module(includes = {NetworkModule.class, GsonModule.class, ApplicationModule.class})
public class RetrofitModule {


    @Provides
    @Singleton
    @Named("nearsoftBookApi")
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson )
    {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(UrlProvider.bookApiUrl)
                .build();
    }



    @Provides
    @Singleton
    public BookRetrofitApi exampleRetrofitApi(@Named("nearsoftBookApi") Retrofit retrofit)
    {
        return retrofit.create(BookRetrofitApi.class);
    }

    @Provides
    @Singleton
    public BookRetrofitApi2 exampleRetrofitApi2(@Named("nearsoftBookApi") Retrofit retrofit)
    {
        return retrofit.create(BookRetrofitApi2.class);
    }


}
