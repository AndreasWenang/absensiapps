package com.example.absensiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startup extends AppCompatActivity {

    Button btn_tambah, btn_lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        btn_tambah= findViewById(R.id.btn_tambah);
        btn_lihat= findViewById(R.id.btn_lihat);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startup.this, Input.class)
                        .putExtra("id", "")
                        .putExtra("title", "")
                        .putExtra("kelas", "")
                        .putExtra("jurusan", ""));
            }
        });
        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startup.this, ActivityList.class));
            }
        });
    }
}