package com.a000webhostapp.docsforlife.docsupdater;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageButton ib_IdCard, ib_Passport, ib_License, ib_Bank, ib_Medical, ib_Degree;
    BottomSheetFragment bottomSheetFragment;
    List<Data> dataList;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ib_IdCard = findViewById(R.id.button_id_card);
        ib_Passport = findViewById(R.id.button_passport);
        ib_License = findViewById(R.id.button_license);
        ib_Bank = findViewById(R.id.button_bank_Account);
        ib_Medical = findViewById(R.id.button_Hospital);
        ib_Degree = findViewById(R.id.button_Degree);



        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();


        String[] projection = {DBHelper.COLUMN_DOC_NAME};
        /*String selection = DBHelper.COLUMN_DOC_NAME + " =?";
        String[] selectionArgs = {DBHelper.COLUMN_DOC_NAME};*/
        String sortOrder = DBHelper.COLUMN_DOC_NAME + " ASC";

        SQLiteDatabase sqLiteDatabase = new DBHelper(this).getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                DBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        String data;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            data = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DOC_NAME));
            dataList.add(new Data(data, R.drawable.ic_perm_identity_black_24dp));


        }

        cursor.close();

        DataAdapter adapter = new DataAdapter(this, dataList);

        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });


    }


    public void onButtonClicked(View view) {

        switch (view.getId()) {

            case R.id.button_id_card: {
                Intent intent = new Intent(MainActivity.this, IdentityCardForm.class);
                bottomSheetFragment.dismiss();
                startActivity(intent);
                Toast.makeText(this, "Id Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_passport: {
                Toast.makeText(this, "Passport Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_license: {
                Toast.makeText(this, "License Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_bank_Account: {
                Toast.makeText(this, "Bank Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_Hospital: {
                Toast.makeText(this, "Hospital Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_Degree: {
                Toast.makeText(this, "Degree Button Clicked", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /*setIconInMenu(menu,R.id.overFlowMenu_NewFolder,R.string.overflowmenu_newfolder,R.mipmap.ic_create_new_folder_black_24dp);*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.overFlowMenu_NewFolder: {
                Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }



}
