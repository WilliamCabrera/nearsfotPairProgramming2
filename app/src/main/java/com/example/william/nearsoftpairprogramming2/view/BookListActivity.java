package com.example.william.nearsoftpairprogramming2.view;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;

import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;


import com.example.william.nearsoftpairprogramming2.NearsoftApplication;
import com.example.william.nearsoftpairprogramming2.R;
import com.example.william.nearsoftpairprogramming2.adapter.BookListItemAdapter;
import com.example.william.nearsoftpairprogramming2.interfaces.BookItemClickListerner;
import com.example.william.nearsoftpairprogramming2.model.Book;

import com.example.william.nearsoftpairprogramming2.model.BookRepository;
import com.example.william.nearsoftpairprogramming2.presenter.IPresenter;
import com.example.william.nearsoftpairprogramming2.presenter.Presenter;
import com.example.william.nearsoftpairprogramming2.retrofit.BookRetrofitApi2;
import com.example.william.nearsoftpairprogramming2.util.AdapterType;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookListActivity extends AppCompatActivity implements BookItemClickListerner,IView {


    private RecyclerView bookRecyclerView;
    private BookListItemAdapter bookListItemAdapter;
    private List<Book> books = new Vector<>();
    private boolean isScrolling = true;

    public final static String LIST_STATE_KEY = "recycler_list_state";

    @Inject
    public BookRetrofitApi2 retrofitApi2;

    FloatingActionButton floatingActionButton;

    IPresenter presenter;

    private int startValue = 0;
    private int maxValue = 10;
    private int adapterType = AdapterType.GRIDLAYOUTMANAGER;
    private int REQUEST_INTERNET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ((NearsoftApplication)getApplication())
                .getApplicationComponent()
                .inject(this);

        presenter = new Presenter(this, new BookRepository(retrofitApi2));

        floatingActionButton = (FloatingActionButton)findViewById(R.id.loadBooks) ;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    int permissionCheck = ActivityCompat.checkSelfPermission(BookListActivity.this, Manifest.permission.WRITE_CALENDAR);
                    if(permissionCheck != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(BookListActivity.this, new String[]{Manifest.permission.INTERNET},REQUEST_INTERNET);
                    }
                    else
                    {
                        triggerLoad("android", startValue, maxValue);
                    }
                }
                else
                    triggerLoad("android", startValue, maxValue);
            }
        });

        LinearLayoutManager lm = new LinearLayoutManager(this);
        GridLayoutManager glm = new GridLayoutManager(this,3);

        bookRecyclerView = (RecyclerView)findViewById(R.id.bookListRecyclerView);
        if(adapterType == AdapterType.LINEARLAYOUTMANAGER)
          bookRecyclerView.setLayoutManager(lm);
        else
            bookRecyclerView.setLayoutManager(glm);

        bookListItemAdapter = new BookListItemAdapter(books, BookListActivity.this, adapterType);
        bookRecyclerView.setAdapter(bookListItemAdapter);
        bookRecyclerView = (RecyclerView)findViewById(R.id.bookListRecyclerView);
        bookRecyclerView.addItemDecoration(new ItemSpaceDecoration(3));
        bookRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = 0;

                lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if(lastCompletelyVisibleItemPosition == books.size() - 1)
                {

                        //Toast.makeText(getApplicationContext(),lastCompletelyVisibleItemPosition+ " : "+(books.size() - 1),Toast.LENGTH_SHORT).show();

                       /*
                       startValue+= 10;
                        maxValue+=10;
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            int permissionCheck = ActivityCompat.checkSelfPermission(BookListActivity.this, Manifest.permission.INTERNET);
                            if(permissionCheck != PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions(BookListActivity.this, new String[]{Manifest.permission.INTERNET},REQUEST_INTERNET);
                            }
                            else
                            {
                                triggerLoad("android", startValue, maxValue);
                            }
                        }
                        else
                            triggerLoad("android", startValue, maxValue);*/



                }
            }
        });

    }

    private void triggerLoad(String query, int startValue, int maxValue){

        Toast.makeText(getApplicationContext(),"Request sent to the server, please wait....",Toast.LENGTH_LONG).show();
        floatingActionButton.setEnabled(false);
        presenter.getBooks("android",startValue,maxValue);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("adapterType",adapterType);
        if(books.size() > 0)
            outState.putParcelableArrayList(LIST_STATE_KEY, new ArrayList(books));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
        if(outState != null)
        { outState.putInt("adapterType",adapterType);
            List<Book> savedBooks = outState.getParcelableArrayList(LIST_STATE_KEY);
            for (Book book : savedBooks)
            {
                books.add(book);
            }
            bookListItemAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
        {
            adapterType = savedInstanceState.getInt("adapterType");
            List<Book> savedBooks = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            for (Book book : savedBooks)
            {
                books.add(book);
            }
            bookListItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if(savedInstanceState != null)
        {
            List<Book> savedBooks = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            for (Book book : savedBooks)
            {
                books.add(book);
            }
            bookListItemAdapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle savedInstanceState = intent.getExtras();
        if(savedInstanceState != null)
        {
            List<Book> savedBooks = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            for (Book book : savedBooks)
            {
                books.add(book);
            }
            bookListItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void BookItemListClicked(Book book, int position) {


        Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);

        intent.putExtra("position",position);
        intent.putExtra("book",book);

        intent.putExtra("booklist",new ArrayList(books));
        startActivity(intent);
    }

    @Override
    public void updateView(List<Book> bookList) {

        books = bookList;
        bookListItemAdapter = new BookListItemAdapter(books, BookListActivity.this,adapterType);
        bookRecyclerView.setAdapter(bookListItemAdapter);
        bookListItemAdapter.notifyDataSetChanged();
        floatingActionButton.setEnabled(true);
    }

    @Override
    public void postMessage(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private class ItemSpaceDecoration extends RecyclerView.ItemDecoration
    {
        private int space;

        public ItemSpaceDecoration(int space)
        {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
           outRect.bottom  = space;
           outRect.top  = space;
           outRect.left  = space;
           outRect.right  = space;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_INTERNET)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                triggerLoad("android", startValue, maxValue);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Internet permission was NOT granted.",Toast.LENGTH_SHORT).show(); ;
            }
        }
    }


}
