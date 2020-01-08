package com.example.movieapp.ProfileAndFavoriteActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Main.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.RegisterAndLogin.ChangePasswordActivity;
import com.example.movieapp.User.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Button changePasswordButton;
    private TextView nameFiled, emailField, ageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

        bottomNavigation();

        ListData();

        changePassword();

    }

    private void changePassword() {

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("Email", emailField.getText());
                startActivity(intent);
            }
        });

    }

    private void bottomNavigation() {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_fav:
                        Intent intent2 = new Intent(ProfileActivity.this, FavoriteActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }

    private void init() {
        nameFiled = findViewById(R.id.pTextViewNameField);
        ageField = findViewById(R.id.pTextViewAgeInputField);
        emailField = findViewById(R.id.pTextViewEmailInputField);
        changePasswordButton = findViewById(R.id.buttonChangePassword);
    }

    private void ListData() {
        Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        User user = databaseHelper.profileLogin(intent.getStringExtra("email"));
        Log.d("alma", user.getName());
        nameFiled.setText(user.getName());
        ageField.setText(user.getAge());
        emailField.setText(user.getEmail());
    }
}
