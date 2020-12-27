package com.example.absensiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.absensiapps.databinding.ActivityInputBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Input extends AppCompatActivity {

    ActivityInputBinding binding1;

    private static final String TAG = "percobaan";
    private DatabaseReference database;


    private ProgressDialog loading;

    private String sPid, sPnama, sPkelas, sPjurusan;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding1 = DataBindingUtil.setContentView(this,R.layout.activity_input);

        database = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPnama = getIntent().getStringExtra("title");
        sPkelas = getIntent().getStringExtra("kelas");
        sPjurusan = getIntent().getStringExtra("jurusan");


        binding1.etNama.setText(sPnama);
        binding1.etKelas.setText(sPkelas);
        binding1.etJurusan.setText(sPjurusan);

        if (sPid.equals("")){
            binding1.btnSave.setText("Save");
            binding1.btnCancel.setText("Cancel");
        } else {
            binding1.btnSave.setText("Edit");
            binding1.btnCancel.setText("Delete");
        }


        binding1.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Snama = binding1.etNama.getText().toString();
                String Skelas = binding1.etKelas.getText().toString();
                String Sjurusan = binding1.etJurusan.getText().toString();

                if (binding1.btnSave.getText().equals("Save")){
                    // perintah save

                    if (Snama.equals("")) {
                        binding1.etNama.setError("Silahkan masukkan nama");
                        binding1.etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        binding1.etKelas.setError("Silahkan masukkan kelas");
                        binding1.etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        binding1.etJurusan.setError("Silahkan masukkan jurusan");
                        binding1.etJurusan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(Input.this,
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
                        binding1.etNama.setError("Silahkan masukkan nama");
                        binding1.etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        binding1.etKelas.setError("Silahkan masukkan kelas");
                        binding1.etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        binding1.etJurusan.setError("Silahkan masukkan jurusan");
                        binding1.etJurusan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(Input.this,
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

        binding1.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding1.btnCancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                    database.child("Request")
                            .child(sPid)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Input.this,
                                            "Data Berhasil didelete",
                                            Toast.LENGTH_SHORT).show();
                                    Intent backIntent = new Intent(Input.this, ActivityList.class);
                                    startActivity(backIntent);
                                    finish();


                                }
                            });
                }

            }
        });
    }



            private void submitUser(Requests requests) {
                database.child("Request")
                        .push()
                        .setValue(requests)
                        .addOnCompleteListener(Input.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                loading.dismiss();

                                binding1.etNama.setText("");
                                binding1.etKelas.setText("");
                                binding1.etJurusan.setText("");

                                Toast.makeText(Input.this,
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

                        binding1.etNama.setText("");
                        binding1.etKelas.setText("");
                        binding1.etJurusan.setText("");

                        Toast.makeText(Input.this,
                                "Data Berhasil diedit",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }
}