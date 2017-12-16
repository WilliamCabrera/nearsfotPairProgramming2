package com.example.william.nearsoftpairprogramming2.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.nearsoftpairprogramming2.R;
import com.example.william.nearsoftpairprogramming2.model.Book;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class BookDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Book book;
    private int REQUEST_INTERNET = 1;
    ImageView imageView;


    public Book getBook() {
        return book;
    }

    public BookDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("book",book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable("book");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        TextView autthor = (TextView)view.findViewById(R.id.authorTv);
        TextView published = (TextView)view.findViewById(R.id.publishedDateTv);
        TextView description = (TextView)view.findViewById(R.id.descriptionTv);
        imageView = (ImageView)view.findViewById(R.id.thumbnail);

        autthor.setText(book.getVolumeInfo().getAuthors(). size() > 0  || book.getVolumeInfo().getAuthors() == null ? book.getVolumeInfo().getAuthors().get(0) : "");
        published.setText(book.getVolumeInfo().getPublishedDate());
        description.setText(book.getVolumeInfo().getDescription());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionCheck = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET},REQUEST_INTERNET);
            }
            else
            {
               loadImage(book);
            }
        }
        else
        {
           loadImage(book);
        }



        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("booksaved",book);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
        {
            book = savedInstanceState.getParcelable("booksaved");
        }
    }

    void loadImage(Book book)
    {
        try
        {
            Picasso.with(getActivity())
                    .load(book.getVolumeInfo().getImageLinks().getThumbnail())
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                            //Toast.makeText(getActivity(),"picture found",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getActivity(),"picture not found",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception ex)
        {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_INTERNET)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

              loadImage(book);
            }
            else
            {
                Toast.makeText(getActivity(),"Internet permission was NOT granted.",Toast.LENGTH_SHORT).show(); ;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
