package com.a000webhostapp.docsforlife.docsupdater;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class DisplayActivity extends AppCompatActivity {

    TextInputEditText editText_DocName, editText_FirstName, editText_LastName, editText_IdNo, editText_DOI;
    String id_number;
    FloatingActionButton fab;
    DocumentDetails details;
    //DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        editText_DocName = findViewById(R.id.editText_Display_DocName);
        editText_FirstName = findViewById(R.id.editText_Display_FirstName);
        editText_LastName = findViewById(R.id.editText_Display_LastName);
        editText_IdNo = findViewById(R.id.editText_Display_IdNo);
        editText_DOI = findViewById(R.id.editText_Display_DOI);
        setDisabledEditText();
        //editText_DocName.setEnabled(false);

        fab = findViewById(R.id.fab_Display_Edit);

        Intent intent = getIntent();
        String documentName = intent.getStringExtra("NAME");
        Toast.makeText(this, "Data is " + documentName, Toast.LENGTH_LONG).show();
        /*String doc_name_LongClick = intent.getStringExtra("DOC_NAME");
        Toast.makeText(this,"Doc name is "+doc_name_LongClick,Toast.LENGTH_LONG).show();*/

        /*SQLiteDatabase sqLiteDatabase = new DBHelper(this).getReadableDatabase();

        String[] projection = {DBHelper.COLUMN_DOC_NAME,
                DBHelper.COLUMN_FIRST_NAME,
                DBHelper.COLUMN_LAST_NAME,
                DBHelper.COLUMN_ID_NUMBER,
                DBHelper.COLUMN_DATE_OF_ISSUE
        };

        String selection = DBHelper.COLUMN_DOC_NAME + " =?";
        String[] selectionArgs = {documentName};

        String sortOrder = DBHelper.COLUMN_CREATED_DATE_TIME + " ASC";

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            doc_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DOC_NAME));
            first_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_FIRST_NAME));
            last_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LAST_NAME));
            id_number = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID_NUMBER));
            doi = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE_OF_ISSUE));
        }

        cursor.close();*/


        DBHelper dbHelper = new DBHelper(DisplayActivity.this);
        /*String[] records = new String[4];
        Bundle b = this.getIntent().getExtras();
        records = b.getStringArray("key");*/
        String records[] = dbHelper.onRead(documentName);



        editText_DocName.setText(records[0]);
        editText_FirstName.setText(records[1]);
        editText_LastName.setText(records[2]);
        editText_IdNo.setText(records[3]);
        editText_DOI.setText(records[4]);

        id_number = editText_IdNo.getText().toString().trim();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnabledEditText();
                fab.setImageResource(R.drawable.ic_done_black_24dp);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        details = new DocumentDetails(editText_FirstName.getText().toString().trim(),
                                editText_LastName.getText().toString().trim(),
                                editText_IdNo.getText().toString().trim(),
                                editText_DOI.getText().toString().trim(),
                                editText_DocName.getText().toString().trim());

                        DBHelper db = new DBHelper(DisplayActivity.this);
                        int count = db.onUpdate(details,id_number);

                        Toast.makeText(DisplayActivity.this,"Row Updated "+count,Toast.LENGTH_LONG).show();





                        //Snackbar.make(view,"Details have been updated",Snackbar.LENGTH_LONG).show();
                        Intent i = new Intent(DisplayActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });


            }

        });


    }


    public void setDisabledEditText() {
        editText_DocName.setEnabled(false);
        editText_FirstName.setEnabled(false);
        editText_LastName.setEnabled(false);
        editText_IdNo.setEnabled(false);
        editText_DOI.setEnabled(false);
    }

    public void setEnabledEditText() {
        editText_DocName.setEnabled(true);
        editText_FirstName.setEnabled(true);
        editText_LastName.setEnabled(true);
        editText_IdNo.setEnabled(true);
        editText_DOI.setEnabled(true);
    }

}
