package com.adet.projectakhir_aditya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adet.projectakhir_aditya.db.DbHelper;
import com.adet.projectakhir_aditya.model.Masyarakat;

public class UpdateActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText etNIK, etNama;
    private Button btnSimpan;
    private Masyarakat masyarakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DbHelper(this);

        etNIK = findViewById(R.id.edt_nik);
        etNama = findViewById(R.id.edt_nama);
        btnSimpan = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        masyarakat = (Masyarakat) intent.getSerializableExtra("user");

        etNIK.setText(masyarakat.getNIK());
        etNama.setText(masyarakat.getNama());

        btnSimpan.setOnClickListener((View v) -> {
            dbHelper.updateUser(masyarakat.getId(), etNIK.getText().toString(), etNama.getText().toString());
            Toast.makeText(UpdateActivity.this, "Updated berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}