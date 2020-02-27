package com.trungdaniel.demngayyeunhau;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.trungdaniel.demngayyeunhau.database.UserDAO;
import com.trungdaniel.demngayyeunhau.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity {

    TextView tenNam, tenNu, dayStart, tvYear, tvMonth, tvDay, tvHour, tvMinute, tvSeconds;
    CircularImageView imgNam,imgNu;
    UserDAO userDAO;
    WaveLoadingView mWaveLoadingView;
    Uri imgAvatarUri;
    Bitmap resizedBitmap = null;
    public static final int PICK_IMAGE = 1;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDAO = new UserDAO(this);
        initView();
        imgNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        imgNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (getSizeUser() == 0) {
                        Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                        startActivity(intent);
                        Log.i("tag", "abc");
                    } else {
                        Timer T = new Timer();
                        T.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setInfor();
                                    }
                                });
                            }
                        }, 1000, 1000);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void initView() {
        dayStart = findViewById(R.id.dayStart);
        tenNam = findViewById(R.id.tenNam);
        tenNu = findViewById(R.id.tenNu);
        tvYear = (TextView) findViewById(R.id.nam);
        tvMonth = (TextView) findViewById(R.id.thang);
        tvDay = (TextView) findViewById(R.id.ngay);
        tvHour = (TextView) findViewById(R.id.gio);
        tvMinute = (TextView) findViewById(R.id.phut);
        tvSeconds = (TextView) findViewById(R.id.giay);
        imgNam = findViewById(R.id.anhNam);
        imgNu = findViewById(R.id.anhNu);
        mWaveLoadingView = findViewById(R.id.waveLoadingView);
    }

    @SuppressLint("StaticFieldLeak")
    private void setInfor() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> users = null;
                users = UserDAO.getAllUser();
                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                if (users.size() > 0) {
                    tenNam.setText(users.get(0).getTenBan());
                    tenNu.setText(users.get(0).getTenNguoiAy());
                    dayStart.setText(sdf.format(users.get(0).getDateStart()));
                }
                try {
                    setTime(sdf.format(users.get(0).getDateStart()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private int getSizeUser() throws ParseException {
        final List<User> users = UserDAO.getAllUser();
        Log.d("SIZEE", users.size() + "");
        return users.size();
    }

    @SuppressLint({"StaticFieldLeak", "SetTextI18n"})
    private void setTime(String dayStart) throws ParseException {
        try {
            Date oldDate = sdf.parse(dayStart);
            Date currentDate = new Date();
            final Long diff = currentDate.getTime() - oldDate.getTime();

            if (oldDate.before(currentDate)) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(diff); //truyen vao
                int mYear = c.get(Calendar.YEAR) - 1970;
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;
                int hr = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);
                int sec = c.get(Calendar.SECOND);
                Long day = diff / 86400000;
                int days = day.intValue();

                if (days < 100) {
                    mWaveLoadingView.setProgressValue(days);
                } else if (days < 200) {
                    mWaveLoadingView.setProgressValue(days - 100);
                } else if (days < 300) {
                    mWaveLoadingView.setProgressValue(days - 200);
                } else if (days < 400) {
                    mWaveLoadingView.setProgressValue(days - 300);
                } else if (days < 500) {
                    mWaveLoadingView.setProgressValue(days - 400);
                } else if (days < 600) {
                    mWaveLoadingView.setProgressValue(days - 500);
                } else if (days < 700) {
                    mWaveLoadingView.setProgressValue(days - 600);
                } else if (days < 800) {
                    mWaveLoadingView.setProgressValue(days - 700);
                } else if (days < 900) {
                    mWaveLoadingView.setProgressValue(days - 800);
                } else if (days < 1000) {
                    mWaveLoadingView.setProgressValue(days - 900);
                }
                mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
                mWaveLoadingView.setCenterTitle(day + " Day");
                mWaveLoadingView.pauseAnimation();
                mWaveLoadingView.resumeAnimation();
                mWaveLoadingView.cancelAnimation();
                mWaveLoadingView.startAnimation();

                if (mYear < 10) {
                    tvYear.setText("0" + mYear + " Năm - ");
                } else {
                    tvYear.setText(mYear + " Năm - ");
                }
                if (mMonth < 10) {
                    tvMonth.setText("0" + mMonth + " Tháng - ");
                } else {
                    tvMonth.setText(mMonth + " Tháng - ");
                }
                if (mDay < 10) {
                    tvDay.setText("0" + mDay + " Ngày - ");
                } else {
                    tvDay.setText(mDay + " Ngày - ");
                }
                if (hr < 10) {
                    tvHour.setText("0" + hr + " Giờ - ");
                } else {
                    tvHour.setText(hr + " Giờ - ");
                }
                if (min < 10) {
                    tvMinute.setText("0" + min + " Phút - ");
                } else {
                    tvMinute.setText(min + " Phút - ");
                }
                if (sec < 10) {
                    tvSeconds.setText("0" + sec + " Giây");
                } else {
                    tvSeconds.setText(sec + " Giây");
                }

            } else {
                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
