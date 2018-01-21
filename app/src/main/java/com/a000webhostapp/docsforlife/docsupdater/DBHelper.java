package com.a000webhostapp.docsforlife.docsupdater;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "sample_database";
    static final String TABLE_NAME = "person";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "First_Name";
    private static final String COLUMN_LAST_NAME = "Last_Name";
    private static final String COLUMN_ID_NUMBER = "ID_Number";
    private static final String COLUMN_DATE_OF_ISSUE = "Date_Of_Issue";
    private static final String COLUMN_DATE_OF_EXPIRY = "Date_Of_Expiry";
    static final String COLUMN_DOC_NAME = "Document_Name";
    private static final String COLUMN_CREATED_DATE_TIME = "datetime_created";

    private String doc_name, first_name, last_name, id_number, doi;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT NOT NULL, "
                + COLUMN_LAST_NAME + " TEXT NOT NULL, "
                + COLUMN_ID_NUMBER + " TEXT NOT NULL, "
                + COLUMN_DATE_OF_ISSUE + " TEXT, "
                + COLUMN_DATE_OF_EXPIRY + " TEXT, "
                + COLUMN_DOC_NAME + " TEXT, "
                + COLUMN_CREATED_DATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP " + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long onInsert(DocumentDetails documentDetails) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select " + COLUMN_DOC_NAME + " from " + TABLE_NAME;
        long rowId = 1;
        Cursor cursor = database.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String a = cursor.getString(cursor.getColumnIndex(COLUMN_DOC_NAME));
            if (a.equalsIgnoreCase(documentDetails.getDoc_name())) {
                rowId = 0;
                break;
            } else rowId = 1;

        }
        cursor.close();
        if (rowId == 0) {
            return rowId;
        } else {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, documentDetails.getFirst_name());
            values.put(COLUMN_LAST_NAME, documentDetails.getLast_name());
            values.put(COLUMN_ID_NUMBER, documentDetails.getId_number());
            values.put(COLUMN_DATE_OF_ISSUE, documentDetails.getDate_of_issue());
            values.put(COLUMN_DOC_NAME, documentDetails.getDoc_name());

            rowId = sqLiteDatabase.insert(TABLE_NAME, null, values);

            return rowId;
        }
    }

    public int onUpdate(DocumentDetails documentDetails, String id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, documentDetails.getFirst_name());
        contentValues.put(COLUMN_LAST_NAME, documentDetails.getLast_name());
        contentValues.put(COLUMN_ID_NUMBER, documentDetails.getId_number());
        contentValues.put(COLUMN_DATE_OF_ISSUE, documentDetails.getDate_of_issue());
        contentValues.put(COLUMN_DOC_NAME, documentDetails.getDoc_name());

        String selection = COLUMN_ID_NUMBER + " =?";
        String[] selectionArgs = {id};
        int count;
        count = sqLiteDatabase.update(TABLE_NAME, contentValues, selection, selectionArgs);

        return count;
    }


    public String[] onRead(String documentName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] projection = {COLUMN_DOC_NAME,
                COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_ID_NUMBER, COLUMN_DATE_OF_ISSUE};

        String selection = COLUMN_DOC_NAME + " =?";

        String[] selectionArgs = {documentName};

        String orderBy = COLUMN_CREATED_DATE_TIME + " ASC";

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            doc_name = cursor.getString(cursor.getColumnIndex(COLUMN_DOC_NAME));
            first_name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            last_name = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            id_number = cursor.getString(cursor.getColumnIndex(COLUMN_ID_NUMBER));
            doi = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_ISSUE));
        }
        cursor.close();

        /*Bundle b = new Bundle();
        b.putStringArray("key", new String[]{doc_name,first_name,last_name,id_number,doi});
        Intent i = new Intent();
        i.putExtras(b);*/

        String[] records = new String[]{doc_name, first_name, last_name, id_number, doi};

        return records;

    }

    public int onDelete(String doc_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String selection = COLUMN_DOC_NAME + " Like ?";

        String[] selectionArgs = {doc_name};

        int count = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);

        return count;

    }

    public long onRenameDocName(String original_doc_name, String rename_doc_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "Select " + COLUMN_DOC_NAME + " from " + TABLE_NAME;
        long rowId = 1;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String a = cursor.getString(cursor.getColumnIndex(COLUMN_DOC_NAME));
            if (a.equalsIgnoreCase(doc_name)) {
                rowId = 0;
                break;
            } else rowId = 1;

        }
        cursor.close();
        if (rowId == 0)
            return rowId;
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DOC_NAME, rename_doc_name);


            String selection = COLUMN_DOC_NAME + " Like ?";
            String[] selectionArgs = {original_doc_name};
            long count;
            count = sqLiteDatabase.update(TABLE_NAME, contentValues, selection, selectionArgs);
            return count;

        }


    }


}
