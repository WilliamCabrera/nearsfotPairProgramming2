package com.example.william.nearsoftpairprogramming2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.william.nearsoftpairprogramming2.view.BookListActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openBookList = (Button)findViewById(R.id.openListofBooks);
        openBookList.setOnClickListener( v-> {

            Intent newIntent = new Intent(MainActivity.this, BookListActivity.class);
            startActivity(newIntent);

        });

    }
}
