package com.example.william.nearsoftpairprogramming2.model;

import retrofit2.Call;

/**
 * Created by William on 12/14/2017.
 */

public interface IModel {

    Call<BookBlock> downLoadBooks(String bookType, int startIndex, int maxValue);
}
