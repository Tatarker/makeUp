package com.elmirapervakova.makeup_studio;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton Call, Geo, Make, Hair, Nail, Brows;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call = (ImageButton) findViewById(R.id.Call);
        Geo = (ImageButton) findViewById(R.id.Geo);
        Hair = (ImageButton) findViewById(R.id. Hair);
        Make = (ImageButton) findViewById(R.id.Make);
        Nail = (ImageButton) findViewById(R.id.Nail );
        Brows = (ImageButton) findViewById(R.id.Brows);

    }

    @Override
    public void onClick(View v) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        switch (v.getId()) {
            case R.id.Call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+79871870121"));
                startActivity(intent);
                break;
            case R.id.Geo:
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("geo:55.730843, 49.169131"));
                startActivity(intent1);
                break;
            case R.id.Make:
                Intent intent2 = new Intent(this,Main2Activity.class);
                startActivity(intent2);
                break;
            case R.id.Hair:
                Intent intent3 = new Intent(this,Main3Activity.class);
                startActivity(intent3);
                break;
            case R.id.Nail:
                Intent intent4 = new Intent(this,Main4Activity.class);
                startActivity(intent4);
                break;
            case R.id.Brows:
                Intent intent5 = new Intent(this,Main5Activity.class);
                startActivity(intent5);
                break;


        }

        }
}
