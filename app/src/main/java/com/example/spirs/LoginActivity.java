package com.example.spirs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    private CheckBox cbRememberMe;

    private Button btnLogin;

    private ImageButton btnBack;
    private ImageButton btnGoogle;

    private TextView tvForgotPassword;
    private TextView tvSignUp;

    private SharedPreferences rememberPrefs;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper =
                new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cbRememberMe =
                findViewById(R.id.cbRememberMe);

        btnLogin =
                findViewById(R.id.btnLogin);

        btnBack =
                findViewById(R.id.btnBack);

        btnGoogle =
                findViewById(R.id.btnGoogle);

        tvForgotPassword =
                findViewById(R.id.tvForgotPassword);

        tvSignUp =
                findViewById(R.id.tvSignUp);

        rememberPrefs =
                getSharedPreferences(
                        "LoginPrefs",
                        MODE_PRIVATE
                );

        loadRememberMe();

        if (SessionManager.isLogin(this)) {

            startActivity(
                    new Intent(
                            LoginActivity.this,
                            BerandaActivity.class
                    )
            );

            finish();
        }

        btnBack.setOnClickListener(
                v -> finish()
        );

        tvForgotPassword.setOnClickListener(v -> {

            String phone =
                    "6281234567890";

            String message =
                    "Halo Admin, saya lupa password akun SPIRS.";

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://wa.me/"
                                            + phone
                                            + "?text="
                                            + Uri.encode(message)
                            )
                    );

            startActivity(intent);
        });

        btnGoogle.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://www.google.com"
                            )
                    )
            );
        });

        tvSignUp.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            LoginActivity.this,
                            RegisterActivity.class
                    )
            );
        });

        btnLogin.setOnClickListener(
                v -> loginUser()
        );
    }

    private void loadRememberMe() {

        boolean remember =
                rememberPrefs.getBoolean(
                        "remember",
                        false
                );

        if (remember) {

            etEmail.setText(
                    rememberPrefs.getString(
                            "email",
                            ""
                    )
            );

            cbRememberMe.setChecked(true);
        }
    }

    private void saveRememberMe(String email) {

        SharedPreferences.Editor editor =
                rememberPrefs.edit();

        if (cbRememberMe.isChecked()) {

            editor.putBoolean(
                    "remember",
                    true
            );

            editor.putString(
                    "email",
                    email
            );

        } else {

            editor.clear();
        }

        editor.apply();
    }

    private void loginUser() {

        String email =
                etEmail.getText()
                        .toString()
                        .trim();

        String password =
                etPassword.getText()
                        .toString()
                        .trim();

        if (email.isEmpty()
                || password.isEmpty()) {

            Toast.makeText(
                    this,
                    "Email dan Password wajib diisi",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        boolean success =
                databaseHelper.loginUser(
                        email,
                        password
                );

        if (success) {

            Cursor cursor =
                    databaseHelper.getUser(email);

            String nama = "User";

            if (cursor.moveToFirst()) {

                nama =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_NAMA
                                )
                        );
            }

            cursor.close();

            saveRememberMe(email);

            SessionManager.saveLogin(
                    this,
                    nama,
                    email,
                    "user"
            );

            Toast.makeText(
                    this,
                    "Login berhasil",
                    Toast.LENGTH_SHORT
            ).show();

            startActivity(
                    new Intent(
                            LoginActivity.this,
                            BerandaActivity.class
                    )
            );

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Email atau Password salah",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}