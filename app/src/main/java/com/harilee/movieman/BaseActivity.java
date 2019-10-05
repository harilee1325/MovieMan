package com.harilee.movieman;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.harilee.movieman.Login.LoginFragment;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame
        , new LoginFragment(), "base").addToBackStack(null).commitAllowingStateLoss();
    }
}
