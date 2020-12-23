package com.example.absensiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class percobaan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "percobaan";
    private DatabaseReference database;

    private EditText etNama, etKelas, etJurusan;
    private ProgressDialog loading;
    private Button btn_cancel, btn_save;
    private Spinner spinner1;

    private String sPid, sPnama, sPkelas, sPjurusan;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percobaan);

        database = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPnama = getIntent().getStringExtra("title");
        sPkelas = getIntent().getStringExtra("kelas");
        sPjurusan = getIntent().getStringExtra("jurusan");

        etNama = findViewById(R.id.et_nama);
        etKelas = findViewById(R.id.et_kelas);
        etJurusan = findViewById(R.id.et_jurusan);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.text, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        etNama.setText(sPnama);
        etKelas.setText(sPkelas);
        etJurusan.setText(sPjurusan);

        if (sPid.equals("")){
            btn_save.setText("Save");
            btn_cancel.setText("Cancel");
        } else {
            btn_save.setText("Edit");
            btn_cancel.setText("Delete");
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Snama = etNama.getText().toString();
                String Skelas = etKelas.getText().toString();
                String Sjurusan = etJurusan.getText().toString();

                if (btn_save.getText().equals("Save")){
                    // perintah save

                    if (Snama.equals("")) {
                        etNama.setError("Silahkan masukkan nama");
                        etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        etKelas.setError("Silahkan masukkan kelas");
                        etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        etJurusan.setError("Silahkan masukkan jurusan");
                        etJurusan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(percobaan.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        submitUser(new Requests(
                                Snama.toLowerCase(),
                                Skelas.toLowerCase(),
                                Sjurusan.toLowerCase()));

                    }
                } else {
                    // perintah edit
                    if (Snama.equals("")) {
                        etNama.setError("Silahkan masukkan nama");
                        etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        etKelas.setError("Silahkan masukkan kelas");
                        etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        etJurusan.setError("Silahkan masukkan jurusan");
                        etJurusan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(percobaan.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        editUser(new Requests(
                                Snama.toLowerCase(),
                                Skelas.toLowerCase(),
                                Sjurusan.toLowerCase()), sPid);

                    }
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_cancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                }

            }
        });
    }



            private void submitUser(Requests requests) {
                database.child("Request")
                        .push()
                        .setValue(requests)
                        .addOnCompleteListener(percobaan.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                loading.dismiss();

                                etNama.setText("");
                                etKelas.setText("");
                                etJurusan.setText("");

                                Toast.makeText(percobaan.this,
                                        "Data Berhasil ditambahkan",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


            }

    private void editUser(Requests requests, String id) {
        database.child("Request")
                .child(id)
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        etNama.setText("");
                        etKelas.setText("");
                        etJurusan.setText("");

                        Toast.makeText(percobaan.this,
                                "Data Berhasil diedit",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}