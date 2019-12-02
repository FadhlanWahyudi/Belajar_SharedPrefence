package com.example.belajar_sharedprefence;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {
    TextInputLayout tinputname,tinputpass,tinputemail,ticonfirmpass;
    AutoCompleteTextView etname,etemail,etpass,etconfirmpass;
    RadioButton  rbmale,rbfemale;
    Button  btregister;
    CheckBox agre;
    RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname        = findViewById(R.id.etUserName);
        etname.setOnClickListener(this);
        etemail       = findViewById(R.id.etemail);
        etpass        = findViewById(R.id.etPass);
        etconfirmpass = findViewById(R.id.etPassConfirm);
        rbmale        = findViewById(R.id.male);
        rbfemale      = findViewById(R.id.female);
        btregister    = findViewById(R.id.btregister);
        agre          = findViewById(R.id.agre);
        gender        = findViewById(R.id.gender);

        btregister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btregister) {
            String nama    = etname.getText().toString();
            String email   = etemail.getText().toString();
            String pass    = etpass.getText().toString();
            String confirm = etconfirmpass.getText().toString();
            String agree   = agre.getText().toString();
            String kelamin = String.valueOf(gender.getCheckedRadioButtonId());



            if (TextUtils.isEmpty(nama)) {
                etname.setError("Nama Tidak Boleh Kosong");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                etemail.setError("Email Tidak Boleh Kosong");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                etpass.setError("Password Tidak Boleh Kosong");
                return;
            }
            if (TextUtils.isEmpty(confirm)) {
                etconfirmpass.setError("Password Tidak Boleh Kosong");
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Password Tidak sama", Toast.LENGTH_SHORT).show();
                return;
            }

            invalidUser(nama, email, pass, kelamin, agree);





        }

    }
    private void invalidUser(String nama, String email, String password, String kelamin, String agree){
        SaveShared usershare = new SaveShared(this);
        UserModel userModel = usershare.getuser();

        String saveEmail = userModel.getEmail();
        if (email.equals(saveEmail)){
            Toast.makeText(this, "Gmail Sudah Terdaftar!", Toast.LENGTH_SHORT).show();
        }else {
            saveUser(nama, email, kelamin, password, agree);
        }

    }



    private void saveUser(String nama, String email, String password, String kelamin, String agree){
        SaveShared userShared = new SaveShared(this);
        UserModel userModel = new UserModel();
        userModel = userShared.getuser();
        userModel.setNama(nama);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setKelamin(kelamin);
        userModel.setAgree(agree);

        userShared.setUser(userModel);
//        Toast.makeText(this, getString(R.string.succesRegister), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
        alert.setTitle(getString(R.string.succesRegister));
        alert.setTitle(getString(R.string.cautionsucces));
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();

            }
        });
        alert.show();
    }
}
