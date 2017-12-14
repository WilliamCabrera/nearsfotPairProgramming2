package com.example.william.nearsoftpairprogramming2.retrofit;



import com.example.william.nearsoftpairprogramming2.model.Book;
import com.example.william.nearsoftpairprogramming2.model.BookBlock;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by William on 12/13/2017.
 */


public interface BookRetrofitApi {


    @GET("volumes/{volumeId}")
    Observable<Book> getBookbyId(@Path("volumeId") String bookId);

    @GET("volumes")
    Observable<BookBlock> getBooks(
            @Query("q") String query,
            @Query("startIndex") Integer startIndex,
            @Query("maxResults") Integer maxResults
    );

}
