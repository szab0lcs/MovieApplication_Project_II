package com.example.movieapp.RegisterAndLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button changePassword;
    private EditText email, passwordFirst, passwordSecond;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();

        changePass();
    }

    private void changePass() {

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null){

            String string = (String) bundle.get("Email");
            email.setText(string);
        }


       changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!passwordFirst.getText().toString().isEmpty()){

                    if(passwordFirst.getText().toString().equals(passwordSecond.getText().toString())){

                        boolean isUpdate = databaseHelper.updatePassword(email.getText().toString(), passwordFirst.getText().toString());
                        if(!isUpdate){

                            Toast.makeText(ChangePasswordActivity.this, "Not changed password",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ChangePasswordActivity.this, "Changed password",Toast.LENGTH_SHORT).show();
                            passwordFirst.setText("");
                            passwordSecond.setText("");

                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "The passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init() {

        changePassword = findViewById(R.id.cButtonConfirm);
        email = findViewById(R.id.cEditTextEmail);
        passwordFirst = findViewById(R.id.cEditTextFirst);
        passwordSecond = findViewById(R.id.cEditTextSecond);
        databaseHelper = new DatabaseHelper(this);

    }
}
