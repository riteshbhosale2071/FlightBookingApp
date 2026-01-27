package com.example.Project1.Activity;

import android.content.Intent;
import android.os.Bundle;
import com.example.Project1.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
private ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupBtn.setOnClickListener(v ->
                startActivity(new Intent(IntroActivity.this, SignUpActivity.class))
        );

        binding.loginBtn.setOnClickListener(v ->
                startActivity(new Intent(IntroActivity.this, LoginActivity.class))
        );

    }
}