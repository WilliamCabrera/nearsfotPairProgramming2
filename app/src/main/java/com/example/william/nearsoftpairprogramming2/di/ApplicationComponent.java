package com.example.william.nearsoftpairprogramming2.di;





import com.example.william.nearsoftpairprogramming2.view.BookDetailActivity;
import com.example.william.nearsoftpairprogramming2.view.BookListActivity;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by William on 12/11/2017.
 */
@Singleton
@Component(modules = { ApplicationModule.class, ContextModule.class,GsonModule.class, NetworkModule.class,PicassoModule.class,RetrofitModule.class})
public interface ApplicationComponent {

    void inject(BookListActivity createActivity);
    void inject(BookDetailActivity createActivity);



}
