package com.sample.ui.data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class SharedPreferencesActivity extends AppCompatActivity {

    private static final String SP_KEY = "BMI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferences);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTexHeight = findViewById(R.id.editTexHeight);
        EditText editTexWeight = findViewById(R.id.editTexWeight);
        TextView result = findViewById(R.id.result);
        Button submit = findViewById(R.id.submit);
        result.setText("前次紀錄：" + getBMI());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "" + editTextName.getText();
                String heightString = "" + editTexHeight.getText();
                String weightString = "" + editTexWeight.getText();

                try {
                    float height = Integer.parseInt(heightString) / 100.f;
                    int weight = Integer.parseInt(weightString);
                    float bmi = weight / (height * height);
                    result.setText(name + " 的BMI=" + bmi);
                    setBMI(name + " 的BMI=" + bmi);
                } catch (Exception e) {
                    Toast.makeText(SharedPreferencesActivity.this, "請輸入資料", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String getBMI() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SharedPreferencesActivity.this);
        return sp.getString(SP_KEY, "查無資料");
    }

    public void setBMI(String value) {
        if (value.isEmpty()) return;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SharedPreferencesActivity.this);
        sp.edit().putString(SP_KEY, value).commit();
    }
}