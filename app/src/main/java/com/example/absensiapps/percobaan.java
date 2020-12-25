package com.example.absensiapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.ActionMenuViewBindingAdapter;

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

import com.example.absensiapps.databinding.ActivityPercobaanBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class percobaan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityPercobaanBinding binding;

    private static final String TAG = "percobaan";
    private DatabaseReference database;


    private ProgressDialog loading;

    private String sPid, sPnama, sPkelas, sPjurusan;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_percobaan);

        database = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPnama = getIntent().getStringExtra("title");
        sPkelas = getIntent().getStringExtra("kelas");
        sPjurusan = getIntent().getStringExtra("jurusan");


        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.text, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner1.setAdapter(adapter);
        binding.spinner1.setOnItemSelectedListener(this);

        binding.etNama.setText(sPnama);
        binding.etKelas.setText(sPkelas);
        binding.etJurusan.setText(sPjurusan);

        if (sPid.equals("")){
            binding.btnSave.setText("Save");
            binding.btnCancel.setText("Cancel");
        } else {
            binding.btnSave.setText("Edit");
            binding.btnCancel.setText("Delete");
        }


        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Snama = binding.etNama.getText().toString();
                String Skelas = binding.etKelas.getText().toString();
                String Sjurusan = binding.etJurusan.getText().toString();

                if (binding.btnSave.getText().equals("Save")){
                    // perintah save

                    if (Snama.equals("")) {
                        binding.etNama.setError("Silahkan masukkan nama");
                        binding.etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        binding.etKelas.setError("Silahkan masukkan kelas");
                        binding.etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        binding.etJurusan.setError("Silahkan masukkan jurusan");
                        binding.etJurusan.requestFocus();
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
                        binding.etNama.setError("Silahkan masukkan nama");
                        binding.etNama.requestFocus();
                    } else if (Skelas.equals("")) {
                        binding.etKelas.setError("Silahkan masukkan kelas");
                        binding.etKelas.requestFocus();
                    } else if (Sjurusan.equals("")) {
                        binding.etJurusan.setError("Silahkan masukkan jurusan");
                        binding.etJurusan.requestFocus();
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

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.btnCancel.getText().equals("Cancel")) {
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

                                binding.etNama.setText("");
                                binding.etKelas.setText("");
                                binding.etJurusan.setText("");

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

                        binding.etNama.setText("");
                        binding.etKelas.setText("");
                        binding.etJurusan.setText("");

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