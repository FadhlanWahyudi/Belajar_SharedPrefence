package com.example.belajar_sharedprefence;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText tinama, tiemail, tiPassword, tiPasswordConfirm;
    Button btConfirm;
    UserModel userModel;
    SaveShared saveShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tinama = findViewById(R.id.tiEditText);
        tiemail = findViewById(R.id.tiEditEmail);
        tiPassword = findViewById(R.id.tiEditPassword);
        tiPasswordConfirm = findViewById(R.id.tiEditPassword);
        btConfirm = findViewById(R.id.btconfirm);
        saveShared = new SaveShared(this);

        setData();
        btConfirm.setOnClickListener(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tiemail.setFocusable(false);

    }

    private void setData() {
        userModel = saveShared.getuser();
        String nama = userModel.getNama();
        String email = userModel.getEmail();
        String pass = userModel.getPassword();

        tinama.setText(nama);
        tiemail.setText(email);
        tiPassword.setText(pass);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btconfirm:
                confirmSetting();
                finish();
                break;
        }

    }


    private void confirmSetting() {
        userModel = saveShared.getuser();
        String confirmPassword = tiPasswordConfirm.getText().toString();
        String oldPassword = userModel.getPassword();
        if (confirmPassword.equals(oldPassword)) {
            userModel.setNama(tinama.getText().toString());
            userModel.setPassword(tiPassword.getText().toString());
            saveShared.setUser(userModel);
            Toast.makeText(this, "Data Berhasil Diganti!", Toast.LENGTH_SHORT).show();

        }
    }


}
