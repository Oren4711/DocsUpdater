package com.a000webhostapp.docsforlife.docsupdater;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;


public class IdentityCardForm extends AppCompatActivity {
    DocumentDetails documentDetails;

    private TextInputEditText first_name, last_name, id_number, date_of_issue,doc_name;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DatePickerDialog datePickerDialog;
    private int maxYear, maxMonth, maxDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_card_form);

        first_name = findViewById(R.id.editText_First_Name);
        last_name = findViewById(R.id.editText_Last_Name);
        id_number = findViewById(R.id.editText_id);
        doc_name = findViewById(R.id.editText_Doc_Name);


        date_of_issue = findViewById(R.id.editText_Date_of_Issue);
        date_of_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                maxYear = calendar.get(Calendar.YEAR);
                maxMonth = calendar.get(Calendar.MONTH);
                maxDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(IdentityCardForm.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener, maxYear, maxMonth, maxDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

                /* *********************Select A theme later on************* */
                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                datePickerDialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String date = month + 1 + "/" + day + "/" + year;
                date_of_issue.setText(date);


            }

        };



    }

    public void onSubmitButtonClicked(View view) {

        documentDetails = new DocumentDetails(first_name.getText().toString().trim(),
                last_name.getText().toString().trim(), id_number.getText().toString().trim(),
                date_of_issue.getText().toString().trim(), doc_name.getText().toString().trim());

        DBHelper dbHelper = new DBHelper(this);
        long rowId = dbHelper.onInsert(documentDetails);
        Log.i("Row Id is ", Long.toString(rowId));
        if (rowId==0){
            doc_name.setError("Document Name already exists");
        }else {

        Toast.makeText(this, "Details added successfully! ", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(IdentityCardForm.this, MainActivity.class);
        startActivity(intent);

        finish();}



    }


}
