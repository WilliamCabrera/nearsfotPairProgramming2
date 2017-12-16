package com.example.william.nearsoftpairprogramming2.view;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.AsyncTask;
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

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.william.nearsoftpairprogramming2.NearsoftApplication;
import com.example.william.nearsoftpairprogramming2.R;
import com.example.william.nearsoftpairprogramming2.adapter.BookListItemAdapter;
import com.example.william.nearsoftpairprogramming2.interfaces.BookItemClickListerner;
import com.example.william.nearsoftpairprogramming2.model.Book;

import com.example.william.nearsoftpairprogramming2.model.BookRepository;
import com.example.william.nearsoftpairprogramming2.model.ImageLink;
import com.example.william.nearsoftpairprogramming2.model.VolumenInfo;
import com.example.william.nearsoftpairprogramming2.presenter.IPresenter;
import com.example.william.nearsoftpairprogramming2.presenter.Presenter;
import com.example.william.nearsoftpairprogramming2.retrofit.BookRetrofitApi2;
import com.example.william.nearsoftpairprogramming2.util.AdapterType;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

public class BookListActivity extends AppCompatActivity implements BookItemClickListerner,IView {


    private ProgressBar progressBar;
    private RecyclerView bookRecyclerView;
    private BookListItemAdapter bookListItemAdapter;
    private List<Book> books = new Vector<>();
    private boolean isScrolling = true;

    public final static String LIST_STATE_KEY = "recycler_list_state";

    @Inject
    public BookRetrofitApi2 retrofitApi2;

    FloatingActionButton floatingActionButton;

    IPresenter presenter;
    LinearLayoutManager lm;
    GridLayoutManager glm;

    private int startValue = 0;
    private int maxValue = 20;
    private int adapterType = AdapterType.LINEARLAYOUTMANAGER;
    private int REQUEST_INTERNET = 1;

    private static final int MAX_ITEMS_PER_REQUEST = 20;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ((NearsoftApplication)getApplication())
                .getApplicationComponent()
                .inject(this);

        presenter = new Presenter(this, new BookRepository(retrofitApi2));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
                {

                    triggerLoad("android", startValue, maxValue);
                }

            }
        });

        lm = new LinearLayoutManager(this);
        glm = new GridLayoutManager(this,3);

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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final int visibleItemsCount = (adapterType == AdapterType.LINEARLAYOUTMANAGER ?  lm.getChildCount() : glm.getChildCount());
                final int totalItemsCount = ( adapterType == AdapterType.LINEARLAYOUTMANAGER ?  lm.getItemCount(): glm.getItemCount());
                final int pastVisibleItemsCount = (  adapterType == AdapterType.LINEARLAYOUTMANAGER ? lm.findFirstVisibleItemPosition() : glm.findFirstVisibleItemPosition());
                final boolean lastItemShown = visibleItemsCount + pastVisibleItemsCount >= totalItemsCount;


                Log.d("------",String.format("visibleItemsCount: %s, totalItemsCount: %s, pastVisibleItemsCount: %s, lastItemShown: %s",visibleItemsCount,totalItemsCount,pastVisibleItemsCount,lastItemShown));

                if(lastItemShown)
                {

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        int permissionCheck = ActivityCompat.checkSelfPermission(BookListActivity.this, Manifest.permission.WRITE_CALENDAR);
                        if(permissionCheck == PackageManager.PERMISSION_GRANTED)
                        {
                            startValue+= 10;
                            triggerLoad("android", startValue, maxValue);
                        }

                    }
                    else
                    {
                        startValue+= 10;
                        triggerLoad("android", startValue, maxValue);
                    }
                }
            }
        });


    }



    private void triggerLoad(String query, int startValue, int maxValue){

        if(books.size() < 60)
        {
            progressBar.setVisibility(View.VISIBLE);
            floatingActionButton.setEnabled(false);
            presenter.getBooks("android",startValue,maxValue);
        }
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
            if(savedBooks!=null)
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
    public void notifyChangeToRecycler(List<Book> bookList) {

        books.addAll(bookList);
        //books = bookList;
        bookListItemAdapter = new BookListItemAdapter(books, BookListActivity.this,adapterType);
        bookRecyclerView.setAdapter(bookListItemAdapter);
        bookListItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateView() {

        floatingActionButton.setEnabled(true);
        if(progressBar.getVisibility() == View.VISIBLE)
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void postMessage(String msg) {

        floatingActionButton.setEnabled(true);
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



    private void simulateLoading() {
        new AsyncTask<Void, Void, Void>() {
            @Override protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("MainActivity", e.getMessage());
                }
                return null;
            }

            @Override protected void onPostExecute(Void param) {
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    private Book fakeBook(String name, String author, String date)
    {
        List<String> authors = new Vector<>();
        authors.add(author);
        VolumenInfo volumenInfo = new VolumenInfo();
        volumenInfo.setAuthors(authors);
        volumenInfo.setDescription(name+" : "+ author+" : "+date);
        volumenInfo.setPublishedDate(date);
        volumenInfo.setTitle(name);
        volumenInfo.setImageLinks(new ImageLink(""));
        Book book = new Book();
        book.setVolumeInfo(volumenInfo);
        return  book;
    }

    private List<Book> generateListOfBooks()
    {
        List<Book> result = new Vector<>();

        for (int i = 0; i< 20; i ++)
        {
            result.add(fakeBook(" title: "+i, "author: "+ i,"date: "+i));
        }
        return result;
    }


}
