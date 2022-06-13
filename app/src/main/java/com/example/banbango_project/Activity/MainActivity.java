package com.example.banbango_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banbango_project.R;

public class MainActivity extends AppCompatActivity {
    ImageView tmblban;
    Button btnsettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tmblban = findViewById(R.id.tmblban);
        tmblban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CariLokasiActivity.class);
                startActivity(intent);
            }
        });

        btnsettings = findViewById(R.id.btnsettings);
        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        Intent intent =getIntent();
        String namauser = (String) intent.getSerializableExtra("");

    }
}