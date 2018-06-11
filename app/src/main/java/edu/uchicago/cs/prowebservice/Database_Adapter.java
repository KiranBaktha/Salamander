package edu.uchicago.cs.prowebservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database_Adapter {

    //Column Names
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_IMAGE_URL = "image_url";
    public static final String COL_DESCRIPTION = "description";

    //Corresponding Indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_IMAGE_URL = 2;
    public static final int INDEX_DESCRIPTION = 3;

    //used for logging
    private static final String TAG = "BucketList_DbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "db_holder";
    private static final String TABLE_NAME = "fav_table";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_TITLE + " TEXT, " +
                    COL_IMAGE_URL + " TEXT, " +
                    COL_DESCRIPTION + " TEXT );";


    public Database_Adapter(Context ctx) {
        this.mCtx = ctx;
    }

    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //create an entry
    public void createEntry(String title, String img_url, String description) {
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_IMAGE_URL, img_url);
        values.put(COL_DESCRIPTION, description);
        mDb.insert(TABLE_NAME, null, values);
    }

    // read
    public Cursor fetchAll() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_TITLE, COL_IMAGE_URL, COL_DESCRIPTION},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // delete all (Used only when needed)
    public void deleteAll() {
        mDb.delete(TABLE_NAME, null, null);
    }

    // detele article (title is assumed to be a unique indentifier in this case)
    public void deleteArticle(String title) {
        mDb.delete(TABLE_NAME, COL_TITLE + "=?", new String[]{title});
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
