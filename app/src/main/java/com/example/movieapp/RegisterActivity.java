package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameUser;
    private EditText ageUser;
    private EditText emailUser;
    private EditText passwordUser;
    private EditText confirmPasswordUser;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        create();
        register();

    }

    private void register() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameOfUser = nameUser.getText().toString();
                String ageOfUser = ageUser.getText().toString();
                String emailOfUser = emailUser.getText().toString();
                String firstPassword = passwordUser.getText().toString();
                String secondPassword = confirmPasswordUser.getText().toString();

                if(fullNameOfUser.isEmpty()||ageOfUser.isEmpty()||emailOfUser.isEmpty()||firstPassword.isEmpty()||secondPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter another data", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(firstPassword.equals(secondPassword)){
                        Boolean checkemail = databaseHelper.checkEmail(emailOfUser);
                        if(checkemail==true){
                            Boolean insert = databaseHelper.addUser(fullNameOfUser, ageOfUser, emailOfUser, firstPassword);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(), "Successfully registered!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email is already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password not matching", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    private void create() {
        databaseHelper = new DatabaseHelper(this);
        nameUser = findViewById(R.id.name);
        ageUser = findViewById(R.id.age);
        emailUser = findViewById(R.id.email);
        passwordUser = findViewById(R.id.password1);
        confirmPasswordUser = findViewById(R.id.password2);
        registerButton = findViewById(R.id.btn_register);
    }
}