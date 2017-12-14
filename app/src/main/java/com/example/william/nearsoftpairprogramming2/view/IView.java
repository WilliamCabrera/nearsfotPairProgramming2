package com.example.william.nearsoftpairprogramming2.view;

import com.example.william.nearsoftpairprogramming2.model.Book;

import java.util.List;

/**
 * Created by William on 12/14/2017.
 */

public interface IView {

    void updateView(List<Book> bookList);

    void postMessage(String msg);


}
