package com.example.william.nearsoftpairprogramming2.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.william.nearsoftpairprogramming2.NearsoftApplication;
import com.example.william.nearsoftpairprogramming2.R;
import com.example.william.nearsoftpairprogramming2.adapter.PagerAdapter;
import com.example.william.nearsoftpairprogramming2.fragments.BookDetailFragment;
import com.example.william.nearsoftpairprogramming2.model.Book;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BookDetailActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private List<Fragment> fragments = new Vector<>();
    private int position = 0;
    public final static String LIST_STATE_KEY = "recycler_list_state";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        pager = (ViewPager)findViewById(R.id.view_pager);



        ((NearsoftApplication)getApplication())
                .getApplicationComponent()
                .inject(this);

        Bundle bundle = getIntent().getExtras();
        Book bookPassed = bundle.getParcelable("book");
        position = bundle.getInt("position");
        List<Book> savedBooks =  bundle.getParcelableArrayList("booklist");

        createFragments(savedBooks);
        initUI();
        //Toast.makeText(getApplicationContext(),position+" : "+bookPassed.getVolumeInfo().getTitle(),Toast.LENGTH_SHORT).show();

    }

    private void createFragments(List<Book> books)
    {
        for(Book item: books)
        {
            //Log.d("url thubmnail: "+item.getId(),item.getVolumeInfo()+"");
            fragments.add(BookDetailFragment.newInstance(item));
        }
    }

    private void initUI()
    {

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(mPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int positionIndex) {

                position = positionIndex;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("position",position);
        ArrayList<Book> books = new ArrayList<>();
        for(Fragment fg : fragments)
        {
            books.add(((BookDetailFragment)fg).getBook());
        }


        if(books.size() > 0)
            outState.putParcelableArrayList(LIST_STATE_KEY, books);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
        outState.putInt("position",position);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("position");

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        position = savedInstanceState.getInt("position");

    }
}
