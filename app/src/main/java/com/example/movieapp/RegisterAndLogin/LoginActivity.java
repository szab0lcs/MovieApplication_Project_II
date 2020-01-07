package com.example.movieapp.RegisterAndLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Main.MainActivity;
import com.example.movieapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private CheckBox mCheckBoxRemember;
    private SharedPreferences mSharedPreference;
    private static final String PREFS_NAME="PreFsFile";
    private EditText password;
    private Button loginButton;
    private TextView textViewRegister;
    private DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        getPreferencesData();

        register();

        loginAndControl();
    }

    private void loginAndControl() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controlCheckBox();
                login();
            }
        });
    }

    private void controlCheckBox() {
        if(mCheckBoxRemember.isChecked()){
            //ha igen akkor meghiv egy fugvenyt ami menti az adatokat
            saveElement();
        }else{
            //ha nincs mentve akkor akkor torli a beirt adatokat

            //mSharedPreference.edit().clear().apply();
            Log.d("alma", "nna");
        }
    }

    private void saveElement() {
        Boolean boolIsChecked = mCheckBoxRemember.isChecked();
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString("pref_email", email.getText().toString());
        editor.putString("pref_pass", password.getText().toString());
        editor.putBoolean("pref_check", boolIsChecked);
        editor.apply();
        Toast.makeText(getApplicationContext(), "Email and password has ben saved", Toast.LENGTH_SHORT).show();
    }

    private void getPreferencesData() {

        //email lekeres ha el volt mentve
        SharedPreferences sp = getApplication().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(sp.contains("pref_email")){
            String e = sp.getString("pref_email", "not found");
            email.setText(e.toString());
        }
        //password le keres ha el volt mentve
        if(sp.contains("pref_pass")){
            String p = sp.getString("pref_pass", "not found");
            password.setText(p.toString());
        }
        //ha ki volt pippalva akkor maradjon ki pippalva
        if(sp.contains("pref_check")){
            Boolean b = sp.getBoolean("pref_check", false);
            mCheckBoxRemember.setChecked(b);
        }

    }

    private void register() {
        textViewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private void login() {
        String eemail = email.getText().toString();
        String pwd = password.getText().toString();
        Boolean checkLoginUser = mDatabaseHelper.checkLogin(eemail,pwd);

        if(eemail.equals("")||pwd.equals("")){
            Toast.makeText(getApplicationContext(),"Fields are empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (checkLoginUser == true) {
                Toast.makeText(getApplicationContext(), "Login successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("email", eemail);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Wrong email or password!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {

        mCheckBoxRemember = findViewById(R.id.checkBoxRememberMe);
        mSharedPreference = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mDatabaseHelper = new DatabaseHelper(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        textViewRegister = findViewById(R.id.register);
    }
}
