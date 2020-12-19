package com.example.absensiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class percobaan extends AppCompatActivity {

    private static final String TAG = "percobaan";
    private DatabaseReference database;

    private EditText etNama, etEmail, etDeskripsi;
    private Button btnSave;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percobaan);

        database= FirebaseDatabase.getInstance().getReference();

        etNama = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etDeskripsi = findViewById(R.id.et_desk);

        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Snama = etNama.getText().toString();
                String Semail = etEmail.getText().toString();
                String Sdesc = etDeskripsi.getText().toString();

                if (Snama.equals("")) {
                    etNama.setError("Silahkan masukkan usename");
                    etNama.requestFocus();
                } else if (Semail.equals("")) {
                    etEmail.setError("Silahkan masukkan Email");
                    etEmail.requestFocus();
                } else if (Sdesc.equals("")) {
                    etDeskripsi.setError("Silahkan masukkan deskripsi");
                    etDeskripsi.requestFocus();
                } else {
                    loading = ProgressDialog.show( percobaan.this,
                            null,
                            "Please Wait...",
                            true,
                            false);
                    submitUser(new Requests(
                            Snama.toLowerCase(),
                            Semail.toLowerCase(),
                            Sdesc.toLowerCase() ));
                }
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
                                etEmail.setText("");
                                etDeskripsi.setText("");

                                Toast.makeText(percobaan.this,
                                        "Data Berhasil diedit",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }

}