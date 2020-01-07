package com.example.movieapp.RegisterAndLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Main.MainActivity;
import com.example.movieapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    TextView register;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.btn_login);
        register = (TextView)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        try {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String eemail = email.getText().toString();
                    String pwd = password.getText().toString();
                    Boolean checkLoginUser = db.checkLogin(eemail,pwd);

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
            });
        }catch (Exception e){
            Log.d("alma", e.toString());
        }


    }
}