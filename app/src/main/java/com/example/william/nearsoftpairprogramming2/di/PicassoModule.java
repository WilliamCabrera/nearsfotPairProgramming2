package com.example.william.nearsoftpairprogramming2.di;

import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;


/**
 * Created by William on 12/8/2017.
 */
@Module(includes = ContextModule.class)
public class PicassoModule {


    @Provides
    public Picasso picasso(Context context)
    {

        return    new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context))
                .build();
    }
}
