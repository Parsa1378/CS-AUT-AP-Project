package com.example.myshop.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myshop.fragments.SellerRegisterFragment;
import com.example.myshop.model.Product;
import com.example.myshop.model.Seller;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private Context context;
    private static final String NAME = "seller.db";
    private static final String SELLER_TABLE = "SELLER_TABLE";
    private static final String COLUMN_SELLER_NAME = "SELLER_NAME";
    private static final String COLUMN_SELLER_EMAIL = "SELLER_EMAIL";
    private static final String COLUMN_SELLER_PASSWORD = "SELLER_PASSWORD";
    private static final String COLUMN_SELLER_NUMBER = "SELLER_NUMBER";
    private static final String COLUMN_SELLER_LOGIN_COUNT = "SELLER_LOG_COUNT";

    private static final String COLUMN_ID = "ID";






    private static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    private static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";
    private static final String COLUMN_PRODUCT_CATEGORY = "PRODUCT_CATEGORY";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION";
    private static final String COLUMN_PRODUCT_IS_PIN = "PRODUCT_PIN";
    private static final String COLUMN_PRODUCT_RELEASE_DATE = "PRODUCT_RELEASE_DATE";
    private static final String COLUMN_PRODUCT_IMAGE = "PRODUCT_IMAGE";
    private static final String COLUMN_PRODUCT_SELLER_NAME="PRODUCT_SELLER_NAME";
    private static final String COLUMN_PRODUCT_SELLER_PHONE_NUMBER= "SELLER_PHONE_NUMBER";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public DataBaseHandler(@Nullable Context context) {
        super(context,NAME,null,VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String onCreateTableString_Seller = "CREATE TABLE " + SELLER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SELLER_NAME + " TEXT, " + COLUMN_SELLER_EMAIL + " TEXT, " + COLUMN_SELLER_PASSWORD + " TEXT, " + COLUMN_SELLER_NUMBER + " TEXT, "+ COLUMN_SELLER_LOGIN_COUNT + " INT)";
        String createTableStatement_Product = "CREATE TABLE " + PRODUCT_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_PRODUCT_PRICE + " INT, " + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "+COLUMN_PRODUCT_SELLER_NAME+" TEXT, " + COLUMN_PRODUCT_SELLER_PHONE_NUMBER + " TEXT, " + COLUMN_PRODUCT_CATEGORY + " TEXT, " + COLUMN_PRODUCT_IS_PIN + " TEXT, " + COLUMN_PRODUCT_RELEASE_DATE + " TEXT, "+ COLUMN_PRODUCT_IMAGE + " BLOB)";

        db.execSQL(createTableStatement_Product);
        db.execSQL(onCreateTableString_Seller);
    }

    public boolean addSeller(Seller seller) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SELLER_NAME, seller.getFullName());
        cv.put(COLUMN_SELLER_EMAIL, seller.getEmail());
        cv.put(COLUMN_SELLER_NUMBER, seller.getPhoneNumber());
        cv.put(COLUMN_SELLER_PASSWORD, seller.getPassword());
        cv.put(COLUMN_SELLER_LOGIN_COUNT, 1);

        long insert = db.insert(SELLER_TABLE,null, cv);

        if (insert==-1) {
            return false;
        }
        return true;

    }

    public boolean deleteSeller(Seller seller) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStatement = "DELETE FROM " + SELLER_TABLE + " WHERE " + COLUMN_SELLER_EMAIL + " = " + seller.getEmail();

        Cursor cursor = db.rawQuery(queryStatement,null);

        if (cursor.moveToFirst()) {
            return true;
        }

        return false;
    }

    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryStatement = "DELETE FROM " + SELLER_TABLE + ";";

        db.execSQL(queryStatement);
    }

    public Seller getSeller(Seller seller) {

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStatement = "SELECT FROM " + SELLER_TABLE + " WHERE" + COLUMN_SELLER_EMAIL + " = " + seller.getEmail();

        Cursor cursor = db.rawQuery(queryStatement,null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String password = cursor.getString(3);
            String phone = cursor.getString(4);
            int logCount = cursor.getInt(5);
            Seller seller1 = new Seller(name,email,password,phone);
            seller1.setId(id);
            seller1.setLogCount(logCount);
            return seller1;
        }
        return null;
    }

//    public Seller logInSeller(String email, String password) {
//
//    }

//    public boolean deleteOne(Person person) {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        String queryString = "DELETE FROM " + CUSTOMER_TABLE +" WHERE " + COLUMN_ID + " = " + person.getId();
//
//        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);
//        if (cursor.moveToFirst()) {
//            return true;
//        }
//        return false;
//
//    }

//    public List<Person> getEveryone() {
//        List<Person> returnList = new ArrayList<>();
//
//        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(queryString,null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                int age = cursor.getInt(2);
//
//                Person person = new Person(name,age);
//                returnList.add(person);
//            }while (cursor.moveToNext());
//        } else {
//
//        }
//
//        cursor.close();
//        db.close();
//        return returnList;
//    }

    public boolean addProduct(Product product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = product.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        cv.put(COLUMN_PRODUCT_NAME , product.getName());
        cv.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        cv.put(COLUMN_PRODUCT_CATEGORY, product.getCategoryString());
        cv.put(COLUMN_PRODUCT_RELEASE_DATE, product.getReleaseDate().toString());
        cv.put(COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        cv.put(COLUMN_PRODUCT_IS_PIN, product.isPin());
        cv.put(COLUMN_PRODUCT_SELLER_PHONE_NUMBER, product.getSeller().getPhoneNumber());
        cv.put(COLUMN_PRODUCT_IMAGE, imageInBytes);
        cv.put(COLUMN_PRODUCT_SELLER_NAME,product.getSeller().getFullName());
        long insert = db.insert(PRODUCT_TABLE, null, cv);
        if (insert==-1) {
            return false;
        }
        return true;
    }



    public List<Product> getAllProductsOfSeller(Seller seller){
        List<Product> AllProducts = this.getAllProducts();
        List<Product> AllProductsOfSeller = new ArrayList<>();
        for (Product product : AllProducts){
            if(product.getSellerPhoneNumber().equalsIgnoreCase(seller.getPhoneNumber()))
                AllProductsOfSeller.add(product);
        }
        return AllProductsOfSeller;
    }


    public List<Product> getAllProducts()
    {
        try {
            List<Product> products = new LinkedList<>();
            String query = "SELECT * FROM " + PRODUCT_TABLE;
            SQLiteDatabase DB = this.getReadableDatabase();
            Cursor cursor = DB.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                //loop through the table of products
                do {
                    int Id = cursor.getInt(0);
                    String Name = cursor.getString(1);
                    int Price = cursor.getInt(2);
                    String Description = cursor.getString(3);
                    String sellerName = cursor.getString(4);
                    String sellerPhoneNumber = cursor.getString(5);
                    String Category = cursor.getString(6);
                    String isPin = cursor.getString(7);
                    String releaseDate = cursor.getString(8);
                    byte[] imageBytes = cursor.getBlob(9);

                    Bitmap imageProduct = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    boolean isPinBool = isPin.equals("true");
                    Product product = new Product(Name, Price, imageProduct, Description, isPinBool, sellerPhoneNumber, Category);
                    products.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
            DB.close();
            return products;
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
