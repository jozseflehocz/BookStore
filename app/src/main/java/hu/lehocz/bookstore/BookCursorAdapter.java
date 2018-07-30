/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.lehocz.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import hu.lehocz.bookstore.data.BookContract;
import hu.lehocz.bookstore.data.BookProvider;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Find the columns of book attributes that we're interested in
        int idColumnIndex =cursor.getColumnIndex(BookContract.BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);

        // Read the pet attributes from the Cursor for the current pet
        String bookName = cursor.getString(nameColumnIndex);
        int bookPrice = cursor.getInt(priceColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookName)) {
            bookName = context.getString(R.string.unknown_book);
        }

        int bookQuantity = cursor.getInt(quantityColumnIndex);

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(bookName);
        priceTextView.setText(Integer.toString(bookPrice));
        quantityTextView.setText(Integer.toString(bookQuantity));

        final long bookId=cursor.getInt(idColumnIndex);

        LinearLayout quantityLayout=(LinearLayout)view.findViewById(R.id.quantity_layout);
        Button saleButton=(Button)view.findViewById(R.id.sale_button);
        TextView outOfStockView=(TextView)view.findViewById(R.id.out_of_stock);
        if (bookQuantity>0) {
            quantityLayout.setVisibility(view.VISIBLE);
            saleButton.setVisibility(view.VISIBLE);
            outOfStockView.setVisibility(view.GONE);
            saleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    String quantityString = quantityTextView.getText().toString().trim();
                    int quantity = Integer.parseInt(quantityString);
                    values.put(BookContract.BookEntry.COLUMN_QUANTITY, quantity - 1);
                    Uri currentBookUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, bookId);
                    //Log.i("saleclick",currentBookUri.getPath());
                    int rowsAffected = context.getContentResolver().update(currentBookUri, values, null, null);
                }
            });
        }else{
            saleButton.setVisibility(view.GONE);
            quantityLayout.setVisibility(view.GONE);
            outOfStockView.setVisibility(View.VISIBLE);
        }
    }
}