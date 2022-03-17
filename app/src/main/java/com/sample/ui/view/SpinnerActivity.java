package com.sample.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class SpinnerActivity extends AppCompatActivity {

    private String[] yearArray = {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    private String[] monthArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private Spinner m_Spinner_year;
    private Spinner m_Spinner_month;
    private TextView m_TextView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        m_Spinner_year = findViewById(R.id.year);
        m_Spinner_month = findViewById(R.id.month);
        m_TextView_result = findViewById(R.id.result);

        m_Spinner_year.setAdapter(new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_list_item_1, yearArray));
        m_Spinner_month.setAdapter(new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_list_item_1, monthArray));
        m_Spinner_month.getSelectedItemPosition();
        m_Spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearStr = yearArray[position];
                String month = monthArray[m_Spinner_month.getSelectedItemPosition()];
                m_TextView_result.setText("選擇的是" + yearStr + "年" + month + "月");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        m_Spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearStr = yearArray[m_Spinner_year.getSelectedItemPosition()];
                String month = monthArray[m_Spinner_month.getSelectedItemPosition()];
                m_TextView_result.setText("選擇的是" + yearStr + "年" + month + "月");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}