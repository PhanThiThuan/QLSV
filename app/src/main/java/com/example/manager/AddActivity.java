package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;

    Button btnChonHinh, btnChupHinh, btnLuu, btnHuy;
    EditText edtTen, edtSdt;
    ImageView imgHinhDaiDien;
    int id = -1;
    private String SinhVien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControls();
        addEvents();
    }
    private void insert() {
        String ten = edtTen.getText().toString();
        String sdt = edtSdt.getText().toString();

        byte[] image = getbyteArrayFromImageView(imgHinhDaiDien);

        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten",ten);
        contentValues.put("SDT",sdt);
        contentValues.put("IMAGE",image);

        SQLiteDatabase database = Database.initDatabase(this,"EmployeeDB.sqlite");
        database.insert("SinhVien", null, contentValues);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private byte[] getbyteArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte [] ByteArray = stream.toByteArray();
        return ByteArray;
    }



    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("SELECT * FROM "+SinhVien + " WHERE ID = ?", new String []{id + ""});
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String sdt = cursor.getString(2);
        byte[] image = cursor.getBlob(3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imgHinhDaiDien.setImageBitmap(bitmap);
        edtTen.setText(ten);
        edtSdt.setText(sdt);
    }
    private  void addEvents(){
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
            private void choosePhoto() {
            }
        });
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }

            private void takePicture() {
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    //anh xa
    private void addControls() {
        btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        btnChupHinh = (Button) findViewById(R.id.btnChupHinh);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        edtSdt = (EditText) findViewById(R.id.edtSdt);
        edtTen = (EditText) findViewById(R.id.edtTen);

        imgHinhDaiDien = (ImageView) findViewById(R.id.imgHinhDaiDien);

    }
}