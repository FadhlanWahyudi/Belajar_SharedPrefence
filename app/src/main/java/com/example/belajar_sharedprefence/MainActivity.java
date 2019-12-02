package com.example.belajar_sharedprefence;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belajar_sharedprefence.Service.BaseApiService;
import com.example.belajar_sharedprefence.Service.ApiClient;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    UserModel userModel;
    SaveShared saveShared;
    TextView tvFajr,tvSyuruk,tvDhur,tvAsr,tvMagrib,tvIsya,tvCalender,tvLocalitation,tvTime,tvClock;
    TextView txtfajr,txtsunrise,txtduhr,txtAsr,txtMagrib,txtIsya;
    ImageView ivBackground,loc;
    BaseApiService apiService;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveShared = new SaveShared(this);
        tvFajr = findViewById(R.id.tvFajr);
        tvSyuruk = findViewById(R.id.tvSunrise);
        tvDhur = findViewById(R.id.tvDhuhr);
        tvAsr = findViewById(R.id.tvAsr);
        tvMagrib = findViewById(R.id.tvMaghrib);
        tvIsya = findViewById(R.id.tvIsha);
        tvCalender = findViewById(R.id.tvCalender);
        tvLocalitation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvClock = findViewById(R.id.tvClock);
        ivBackground = findViewById(R.id.ivBg);
        loc = findViewById(R.id.loc);
        scrollView = findViewById(R.id.scroll);

//        progressBar = findViewById(R.id.spin_kit);
        Sprite threeBounce = new ThreeBounce();



        // Deklarasi untuk View Textnya
        txtfajr = findViewById(R.id.fajr);
        txtsunrise = findViewById(R.id.sunrise);
        txtduhr = findViewById(R.id.dhuhr);
        txtAsr = findViewById(R.id.asr);
        txtMagrib = findViewById(R.id.maghrib);
        txtIsya = findViewById(R.id.isya);


        apiService = ApiClient.getJadwalService();
        getJadwalSholat("Bekasi");
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etCityName = new EditText(MainActivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("City Name").setMessage("Masukkan Nama Kota yang Di inginkan").setView(etCityName);
                alert.setPositiveButton("Change City ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   final String city = etCityName.getText().toString();
                   getJadwalSholat(city);
                    }
                });
                alert.show();

            }
        });

        showDynamicTime();



    }

    private void showDynamicTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , dd MMMM yyy");
        tvCalender.setText(simpleDateFormat.format(new Date()));

        // setting dynamic mengikuti waktu setempat
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if (time>=4 && time<= 5){
            tvTime.setText(getString(R.string.Fajr));
            ivBackground.setImageResource(R.drawable.pagio);
            txtfajr.setTextColor(getResources().getColor(R.color.hijau));
            txtfajr.setTextColor(getResources().getColor(R.color.hijau));
        }else if (time>= 5 && time<= 6){
            tvTime.setText(getString(R.string.Sunrise));
            ivBackground.setImageResource(R.drawable.pagio);
            txtsunrise.setTextColor(getResources().getColor(R.color.hijau));
            tvSyuruk.setTextColor(getResources().getColor(R.color.hijau));
        }else if (time>=7 && time<= 10) {
            tvTime.setText("SELAMAT PAGI");
            ivBackground.setImageResource(R.drawable.pagi);
            tvTime.setTextColor(getResources().getColor(R.color.hijau));
            tvClock.setTextColor(getResources().getColor(R.color.hijau));
            tvCalender.setTextColor(getResources().getColor(R.color.hijau));
            tvLocalitation.setTextColor(getResources().getColor(R.color.hijau));


        }else if (time>= 11 && time<= 14.35){
            tvTime.setText(getString(R.string.Dhuhr));
            ivBackground.setImageResource(R.drawable.pagi);
            tvTime.setTextColor(getResources().getColor(R.color.hijau));
            tvClock.setTextColor(getResources().getColor(R.color.hijau));
            tvCalender.setTextColor(getResources().getColor(R.color.hijau));
            tvLocalitation.setTextColor(getResources().getColor(R.color.hijau));
            txtduhr.setTextColor(getResources().getColor(R.color.hijau));
            tvDhur.setTextColor(getResources().getColor(R.color.hijau));
        }
        else if (time>= 14.40 && time<= 17) {
            tvTime.setText(getString(R.string.asar));
            ivBackground.setImageResource(R.drawable.sore);
            txtAsr.setTextColor(getResources().getColor(R.color.hijau));
            tvAsr.setTextColor(getResources().getColor(R.color.hijau));
        } else if (time >= 18) {
            ivBackground.setImageResource(R.drawable.malam);
            tvTime.setText(getString(R.string.magrib));
            txtMagrib.setTextColor(getResources().getColor(R.color.hijau));
            tvMagrib.setTextColor(getResources().getColor(R.color.hijau));
        }

        else if (time>= 19 && time<= 24) {
            tvTime.setText(getString(R.string.isyaa));
            ivBackground.setImageResource(R.drawable.malam);
            tvIsya.setTextColor(getResources().getColor(R.color.hijau));
            tvIsya.setTextColor(getResources().getColor(R.color.hijau));
        }


    }


    private void getJadwalSholat(String namaKota){
        Call<ResponseBody> requestApi = apiService.getJadwalSholat(namaKota);
        requestApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("OK")) {
//                            progressBar.setVisibility(View.GONE);
//                            scrollView.setVisibility(View.VISIBLE);
                            String Fajr = jsonObject.getJSONObject("data").getString("Fajr");
                            String Syuruk = jsonObject.getJSONObject("data").getString("Sunrise");
                            String dzuhur = jsonObject.getJSONObject("data").getString("Dhuhr");
                            String Ashar = jsonObject.getJSONObject("data").getString("Asr");
                            String Maghrib = jsonObject.getJSONObject("data").getString("Maghrib");
                            String isya = jsonObject.getJSONObject("data").getString("Isha");
                            String addres = jsonObject.getJSONObject("location").getString("address");


                            //set data to TextView
                            tvFajr.setText(Fajr + "AM");
                            tvSyuruk.setText(Syuruk + "AM");
                            tvDhur.setText(dzuhur + "AM");
                            tvAsr.setText(Ashar + "PM");
                            tvMagrib.setText(Maghrib + "PM");
                            tvIsya.setText(isya + "PM");
                            tvLocalitation.setText(addres);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Bad Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menuLogout:
                userModel = saveShared.getuser();
                userModel.setStatuslogin(false);
                saveShared.setUser(userModel);
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.accountSetting:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;

            case R.id.Adzan:
                startActivity(new Intent(MainActivity.this,Adzan.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
