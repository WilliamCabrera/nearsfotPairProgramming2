package com.example.william.nearsoftpairprogramming2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.william.nearsoftpairprogramming2.R;
import com.example.william.nearsoftpairprogramming2.interfaces.BookItemClickListerner;
import com.example.william.nearsoftpairprogramming2.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by William on 12/13/2017.
 */


public class BookListItemAdapter extends RecyclerView.Adapter<BookListItemAdapter.ItemViewHolder> {

   private  List<Book> bookList;
   private Context context;
   private BookItemClickListerner bookItemClickListerner;
   int adapterType =0;

    public  BookListItemAdapter(List<Book> books,BookItemClickListerner bookItemClickListerner, int adapterType)
    {
        this.bookList = books;
        this.bookItemClickListerner = bookItemClickListerner;
        this.adapterType = adapterType;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(context == null)
            context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate( adapterType == 1 ? R.layout.book_item_layout: R.layout.grid_item_layout, parent, false);
        return new ItemViewHolder(view,adapterType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

            try
            {
                holder.bookTitle .setText(bookList.get(position).getVolumeInfo().getTitle());

                if(adapterType == 1)
                    holder.bookAuthor .setText(bookList.get(position).getVolumeInfo().getAuthors(). size() > 0 ? bookList.get(position).getVolumeInfo().getAuthors().get(0) : "");

                Picasso.with(context)
                        .load(bookList.get(position)
                                .getVolumeInfo().getImageLinks()
                                .getThumbnail()).into(holder.bookPicture);
            }catch (Exception ex)
            {

            }

        holder.setOnclickListener(position);

    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
   {
       TextView bookTitle;
       TextView bookAuthor;
       ImageView bookPicture;

       public ItemViewHolder(View itemView, int adapterType) {

           super(itemView);

           bookTitle = adapterType == 1 ? (TextView) itemView.findViewById(R.id.bookItemTitle) : (TextView) itemView.findViewById(R.id.booktitle);
           bookAuthor = adapterType == 1 ?(TextView) itemView.findViewById(R.id.bookItemAutorName) : null;
           bookPicture = adapterType == 1 ? (ImageView)itemView.findViewById(R.id.bookItemPictureImg): (ImageView)itemView.findViewById(R.id.bookThumb);
       }

       public void setOnclickListener(final int position)
       {
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   bookItemClickListerner.BookItemListClicked(bookList.get(position),position);
                   //Toast.makeText(context, bookList.get(position).getVolumeInfo().getTitle(), Toast.LENGTH_SHORT).show();
               }
           });
       }
   }
}
