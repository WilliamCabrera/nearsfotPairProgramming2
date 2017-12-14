package com.example.william.nearsoftpairprogramming2.model;

import com.example.william.nearsoftpairprogramming2.retrofit.BookRetrofitApi2;

import retrofit2.Call;

/**
 * Created by William on 12/14/2017.
 */

public class BookRepository  implements IModel{


    private BookRetrofitApi2 bookRetrofitApi2;


    public BookRepository(BookRetrofitApi2 bookRetrofitApi) {
        this.bookRetrofitApi2 = bookRetrofitApi;
    }

    @Override
    public Call<BookBlock> downLoadBooks(String bookType, int startIndex, int maxValue) {
        return bookRetrofitApi2.getBooks(bookType,startIndex,maxValue);
    }
}
