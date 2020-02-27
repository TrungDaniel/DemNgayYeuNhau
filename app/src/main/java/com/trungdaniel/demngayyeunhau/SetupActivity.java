package com.trungdaniel.demngayyeunhau;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.trungdaniel.demngayyeunhau.database.DatabaseManager;
import com.trungdaniel.demngayyeunhau.database.UserDAO;
import com.trungdaniel.demngayyeunhau.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetupActivity extends AppCompatActivity {

    DatabaseManager db;
    UserDAO userDAO;
    ImageView imgChooseDate;
    EditText edTenBan, edTenNguoiAy, edDate;
    Button btnSubmit;
    private int mYear, mMonth, mDay;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initView();
        imgChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setInformation();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void setInformation() throws ParseException {
        final String tenBan = edTenBan.getText().toString();
        final String tenNguoiAy = edTenNguoiAy.getText().toString();
        final Date dateStart = sdf.parse(edDate.getText().toString());
        User user = new User(1,edTenBan.getText().toString(),edTenNguoiAy.getText().toString(), sdf.parse(edDate.getText().toString()));
        if (userDAO.inserUser(user) > 0) {
            Toast.makeText(SetupActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(SetupActivity.this, "Thêm thất bại",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        edDate.setText(year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void initView() {
        db = new DatabaseManager(this);
        userDAO = new UserDAO(this);
        imgChooseDate = findViewById(R.id.imgChooseDate);
        edTenBan = findViewById(R.id.edTenBan);
        edTenNguoiAy = findViewById(R.id.edTenNguoiAy);
        edDate = findViewById(R.id.edDateStart);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}
