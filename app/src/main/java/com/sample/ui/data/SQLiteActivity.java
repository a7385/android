package com.sample.ui.data;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;
import com.sample.ui.data.model.BMIData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

    private SqlTable_BMI m_SqlTable_BMI;

    EditText m_EditText_name;
    EditText m_EditText_height;
    EditText m_EditText_weight;

    ListView m_ListView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //建立DB
        SqlHelper sqlHelper = new SqlHelper(SQLiteActivity.this);
        m_SqlTable_BMI = new SqlTable_BMI(sqlHelper);

        setContentView(R.layout.activity_sqlite);
        m_EditText_name = findViewById(R.id.editTextName);
        m_EditText_height = findViewById(R.id.editTexHeight);
        m_EditText_weight = findViewById(R.id.editTexWeight);
        m_ListView_result = findViewById(R.id.result);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBMI();
            }
        });

        getBMIList();

        m_ListView_result.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BMIData data = (BMIData) view.getTag();
                if (null != data) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != m_SqlTable_BMI) {
            m_SqlTable_BMI.closeDb();
        }
    }

    private void updateBMI(BMIData data) {
        m_SqlTable_BMI.updateById(data);
        getBMIList();
    }

    private void deleteBMI(String id) {
        m_SqlTable_BMI.deleteById(id);
        getBMIList();
    }

    private void setBMI() {
        try {
            BMIData data = new BMIData();
            data.name = "" + m_EditText_name.getText();
            data.height = "" + m_EditText_height.getText();
            data.weight = "" + m_EditText_weight.getText();
            String dateString = "";
            SimpleDateFormat format = new SimpleDateFormat(SqlTable_BMI.DATE_FORMAT);
            try {
                dateString = format.format(new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.date = "" + dateString;
            float height = Integer.parseInt(data.height) / 100.f;
            int weight = Integer.parseInt(data.weight);
            float bmi = weight / (height * height);
            data.bmi = "" + bmi;
            m_SqlTable_BMI.insert(data);
            getBMIList();
        } catch (Exception e) {
            Toast.makeText(SQLiteActivity.this, "請輸入正確資料", Toast.LENGTH_LONG).show();
        }
    }

    private void getBMIList() {
        List<BMIData> bmiDataList = m_SqlTable_BMI.getListData();

        if (null != bmiDataList && bmiDataList.size() > 0) {
            m_ListView_result.setAdapter(new BMIAdapter(bmiDataList));
        }
    }


    class BMIAdapter extends BaseAdapter {

        List<BMIData> m_bmiDataList;

        public BMIAdapter(List<BMIData> bmiDataList) {
            this.m_bmiDataList = bmiDataList;
        }

        //宣告會使用到的元件
        class ViewHolder {
            EditText name;
            TextView height;
            TextView weight;
            TextView bmi;
            TextView date;
            Button update;
            Button delete;
        }

        @Override
        public int getCount() {
            return m_bmiDataList.size();
        }

        @Override
        public BMIData getItem(int position) {
            return m_bmiDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(SQLiteActivity.this);
            convertView = _LayoutInflater.inflate(R.layout.view_bmi, null);

            if (convertView != null) {
                BMIData data = m_bmiDataList.get(position);
                //setTag 可設定各種類型資料
                convertView.setTag(data);
                ViewHolder holder = new ViewHolder();

                holder.name = convertView.findViewById(R.id.name);
                holder.height = convertView.findViewById(R.id.height);
                holder.weight = convertView.findViewById(R.id.weight);
                holder.bmi = convertView.findViewById(R.id.bmi);
                holder.date = convertView.findViewById(R.id.date);
                holder.update = convertView.findViewById(R.id.update);
                holder.delete = convertView.findViewById(R.id.delete);

                holder.name.setText(data.name);
                holder.height.setText("身高：" + data.height + "公分");
                holder.weight.setText("體重：" + data.weight + "公斤");
                holder.bmi.setText("BMI:" + data.bmi);
                holder.date.setText("日期：" + data.date);
                holder.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = "" + holder.name.getText();
                        if (!name.isEmpty()) {
                            data.name = name;

                            new AlertDialog.Builder(SQLiteActivity.this)
                                    .setMessage("確定要變更")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            updateBMI(data);
                                        }
                                    })
                                    .setNeutralButton(android.R.string.cancel, null)
                                    .create()
                                    .show();
                        }
                    }
                });
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(SQLiteActivity.this)
                                .setMessage("確定要刪除")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteBMI(data.id);
                                    }
                                })
                                .setNeutralButton(android.R.string.cancel, null)
                                .create()
                                .show();
                    }
                });

            }
            return convertView;
        }
    }
}