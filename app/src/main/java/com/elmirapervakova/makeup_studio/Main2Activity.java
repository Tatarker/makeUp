package com.elmirapervakova.makeup_studio;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements
        View.OnClickListener {

    private EditText editTextDate, stTel, stName, stArtist, stTime, stData;
    private Spinner spinner, spinner1;

    private String[] Artist = {"Илона", "Лейла", "Марго"};
    private String[] Time = {"09:00", "11:00", "13:00", "16:00", "18:00", "20:00"};

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnDatePicker = (Button) findViewById(R.id.btn_Data);
        editTextDate = (EditText) findViewById(R.id.Data);
        btnDatePicker.setOnClickListener(this);

        stTel = (EditText) findViewById(R.id.Tel);
        stName = (EditText) findViewById(R.id.Name);

        spinner = (Spinner) findViewById(R.id.Artist);
        spinner1 = (Spinner) findViewById(R.id.Time);

        Button enterM = (Button) findViewById(R.id.EnterM);

        ArrayAdapter<String> ArtistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Artist);
        ArtistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ArtistAdapter);
        spinner.setPrompt("Выберите мастера");

        ArrayAdapter<String> TimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Time);
        TimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(TimeAdapter);
        spinner1.setPrompt("Выберите время");

        dbHelper = new DBHelper(this);


    }


    public void clickM(View view) {
        String Name = stName.getText().toString();
        String Tel = stTel.getText().toString();
        String Data = editTextDate.getText().toString();
        String Time = spinner1.getSelectedItem().toString();
        String Artist = spinner.getSelectedItem().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor c;

        boolean occupied = false;
        if ((Data.length() == 0) || (Tel.length() < 10) || (Name.length() < 3)) {
            //Тут просто удалил переменные, они только усложнают чтение кода
            Toast toast = Toast.makeText(getApplicationContext(), "ПОЛЯ ЗАПОЛНЕНЫ НЕВЕРНО", Toast.LENGTH_LONG);
            toast.show();
        } else {
            c = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_CONTACTS, null);
            //Чуть упростил SELECT, * значит выбрать все столбцы, их порядок будет как в DBHelper
            if (c.moveToFirst()) {
                do {
                    String data = c.getString(1);
                    String artist = c.getString(0);
                    String time = c.getString(2);
                    //тут поменял индексы местами в соответстии с порядком в DBHelper
                    occupied = occupied || data.equals(Data) && artist.equals(Artist) && time.equals(Time);
                    //переменная означает занято ли. Если хотя-бы одна из записей будет соответствовать новой, то мы запишем сюда True, ведь тогда у нас правая часть от || будет True.
                } while (c.moveToNext());
            }
            if (occupied) {
                Toast toast = Toast.makeText(getApplicationContext(), "ЗАНЯТО", Toast.LENGTH_LONG);
                toast.show();
            } else {

                contentValues.put(DBHelper.KEY_ARTIST, Artist);
                contentValues.put(DBHelper.KEY_DATA, Data);
                contentValues.put(DBHelper.KEY_TIME, Time);
                contentValues.put(DBHelper.KEY_NAME, Name);
                contentValues.put(DBHelper.KEY_TEL, Tel);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                c = database.rawQuery("SELECT " + DBHelper.KEY_NAME + " FROM " + DBHelper.TABLE_CONTACTS, null);
                if (c.moveToLast()) {
                    String name = c.getString(0);
                    Toast toast = Toast.makeText(getApplicationContext(), "Спасибо за запись, " + name, Toast.LENGTH_LONG);
                    toast.show();
                }
                c.close();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_Data:
//                 вызываем диалог с выбором даты
                callDatePicker();
                break;

        }
    }


    private void callDatePicker() {
//         получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

//         инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        editTextDate.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}


