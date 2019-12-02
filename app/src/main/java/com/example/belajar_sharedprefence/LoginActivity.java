package com.example.belajar_sharedprefence;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView etUserName,etPass;
    TextView tvRegister,tvforgot;
    Button btLogin;

    SaveShared saveShared;
    UserModel  userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPass     = findViewById(R.id.etPass);
        tvRegister = findViewById(R.id.tvRegister);
        tvforgot   = findViewById(R.id.tvforgot);
        btLogin   = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvforgot.setOnClickListener(this);

        saveShared = new SaveShared(this);
        checkLogin();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btLogin:
                String username = etUserName.getText().toString();
                String password = etPass.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    etUserName.setError("Username Tidak Boleh Kosong");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPass.setError("Password Tidak Boleh Kosong");
                    return;
                }
                if (!isValidEmail(username)) {
                    Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(this, "Email Tidak terdaftar, Silahkan Register", Toast.LENGTH_SHORT).show();
                validatelogin(username , password);
                break;
            case R.id.tvRegister:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

                break;
            case  R.id.tvforgot:
                validateforgot();
                break;
        }


        }

        private void validateforgot(){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            userModel = saveShared.getuser();
            String showpassword = userModel.getPassword();
            alert.setTitle("Password kamu adalah :" +showpassword);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }

        private void validatelogin(String email, String password) {
           userModel = saveShared.getuser();
           String saveEmail = userModel.getEmail();
           String savepassword = userModel.getPassword();
           if (email.equals(saveEmail)&& password.equals(savepassword)){
               startActivity(new Intent(LoginActivity.this,MainActivity.class));
               userModel.setStatuslogin(true);
               saveShared.setUser(userModel);
               Toast.makeText(this, String.valueOf(userModel.isStatuslogin()), Toast.LENGTH_SHORT).show();
               finish();
           }else {
               AlertDialog.Builder alert = new AlertDialog.Builder(this);
               alert.setTitle(getString(R.string.accountnotRegist));
               alert.setTitle(getString(R.string.pleaseRegist));
               alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                       finish();
                   }
               });
               alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               alert.show();
           }
        }

        private void checkLogin(){
            userModel = saveShared.getuser();
            boolean statuslogin = userModel.isStatuslogin();
            if (statuslogin == true){
                startActivity(new Intent(LoginActivity.this,MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            }
        }


    private boolean isValidEmail(CharSequence email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
