package com.sample.ui.data;

import static com.sample.ui.data.SqlHelper.DB_NAME;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class FileActivity extends AppCompatActivity {

    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/Download/HelloWorld.txt";

    EditText m_EditText_content;
    Button m_Button_open;
    Button m_Button_save;
    Button m_Button_copyDB;

    TextView m_TextView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        m_EditText_content = findViewById(R.id.editTextContent);
        m_Button_open = findViewById(R.id.open);
        m_Button_save = findViewById(R.id.save);
        m_Button_copyDB = findViewById(R.id.copyDB);
        m_TextView_result = findViewById(R.id.result);

        m_Button_open.setOnClickListener(onClickListener);
        m_Button_save.setOnClickListener(onClickListener);
        m_Button_copyDB.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.open:
                    openFile();
                    break;
                case R.id.save:
                    saveFile();
                    break;
                case R.id.copyDB:
                    copyDB();
                    break;
            }
        }
    };

    private synchronized void openFile() {
        String description = "" ;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), "UTF-8"));
            String data = "";
            while ((data = br.readLine()) != null) {
                description += data + "\n";
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (description.isEmpty()) {
            description = "查無內容";
        }
        m_TextView_result.setText(description);
    }


    private synchronized void saveFile() {
        String data = "" + m_EditText_content.getText();
        try {
            File f = new File(FILE_PATH);
            File file = new File(FILE_PATH);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }

    private synchronized void copyDB() {
        try {
            Calendar now = Calendar.getInstance();
            File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), now.getTimeInMillis() + "backup.db");
            File currentDB = getDatabasePath(DB_NAME);
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0, fis.getChannel().size());
                fis.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}