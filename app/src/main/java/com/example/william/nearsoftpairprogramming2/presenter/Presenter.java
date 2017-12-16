package com.example.william.nearsoftpairprogramming2.presenter;



import com.example.william.nearsoftpairprogramming2.model.Book;
import com.example.william.nearsoftpairprogramming2.model.BookBlock;
import com.example.william.nearsoftpairprogramming2.model.IModel;
import com.example.william.nearsoftpairprogramming2.view.IView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William on 12/14/2017.
 */

public class Presenter implements IPresenter {

    private IView view;
    private IModel model;

    public Presenter(IView view, IModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getBooks(String bookType, int startIndex, int maxValue) {

        Call<BookBlock> response = model.downLoadBooks(bookType,startIndex,maxValue);

        response.enqueue(new Callback<BookBlock>() {
            @Override
            public void onResponse(Call<BookBlock> call, Response<BookBlock> response) {

                if(response.isSuccessful())
                {
                       view.notifyChangeToRecycler(response.body().getItems());
                    view.postMessage("Books counts: "+response.body().getItems().size());
                }
                else
                {
                    view.postMessage("Error finding list on server");
                }
                view.updateView();
            }

            @Override
            public void onFailure(Call<BookBlock> call, Throwable t) {

                view.postMessage("Error connecting with server: "+t.getMessage());
                view.updateView();
            }
        });
    }
}
