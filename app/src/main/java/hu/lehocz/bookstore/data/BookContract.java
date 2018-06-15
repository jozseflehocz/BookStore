package hu.lehocz.bookstore.data;

import android.provider.BaseColumns;

public class BookContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        private BookEntry(){}

        /** Name of database table for books */
        public static final String TABLE_NAME = "books";

        /**
         * Unique ID number for the book (only for use in the database table).
         *
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the product.
         *
         * Type: TEXT
         */
        public static final String COLUMN_PRODUCT_NAME ="product_name";

        /**
         * Price of the product.
         *
         * Type: INTEGER
         */
        public static final String COLUMN_PRICE= "product_price";

        /**
         * Qunatity of the product.
         *
         * Type: INTEGER
         */
        public static final String COLUMN_QUANTITY= "product_quantity";

        /**
         * Name of the supplier.
         *
         * Type: TEXT
         */
        public static final String COLUMN_SUPPLIER_NAME ="supplier_name";

        /**
         * Phone number of the supplier.
         *
         * Type: TEXT
         */
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER ="supplier_phone";


    }


}
