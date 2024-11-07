package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactapp.databinding.ActivityNewViewAddBinding;

public class NewViewAdd extends AppCompatActivity {

    private ActivityNewViewAddBinding binding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityNewViewAddBinding.inflate(getLayoutInflater());
        View view = binding2.getRoot();
        setContentView(view);

        binding2.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding2.newName.getText().toString();
                String mobile = binding2.newPhone.getText().toString();
                String email = binding2.newMail.getText().toString();

                // Tạo intent để gửi dữ liệu về MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("mobile", mobile);
                resultIntent.putExtra("email", email);

                // Set kết quả và kết thúc Activity
                setResult(RESULT_OK, resultIntent);
                finish(); // Quay lại MainActivity
            }
        });
    }
}