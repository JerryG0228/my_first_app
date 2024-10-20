package com.example.app;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2;
    CheckBox chkAgree;
    RadioGroup rGroup1;
    RadioButton rdoDog, rdoCat, rdoRabbit;
    Button btnOK;
    ImageView imgPet;

    Button btnZoomIn, btnZoomOut, btnRotate, btnBrighten, btnDarken;
    float scale = 1.0f; // 확대/축소 비율
    float angle = 0f; // 회전 각도
    float brightness = 1.0f; // 기본 밝기 값 (1.0은 원래 밝기)

    String imagePath = "/storage/emulated/0/Download/dog.png";

    private void loadImageFromDisk(String imgPath) {
        Log.d("ImageLoad", "Loading image: " + imgPath); // 이미지 로드 경로 로그 출력
        File imgFile = new File(imgPath); // imgPath 경로로 파일 객체 생성
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); // 파일 경로에서 이미지 로드
            if (bitmap != null) {
                imgPet.setImageBitmap(bitmap); // 이미지를 ImageView에 설정
                Log.d("ImageLoad", "Image loaded successfully"); // 이미지가 성공적으로 로드된 경우
            } else {
                Log.d("ImageLoad", "Failed to decode image"); // 이미지 디코드 실패
            }
        } else {
            Toast.makeText(getApplicationContext(), "이미지 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            Log.d("ImageLoad", "Image file does not exist"); // 이미지 파일이 존재하지 않는 경우
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("애완동물 사진 보기");

        text1 = (TextView) findViewById(R.id.Text1);
        chkAgree = (CheckBox) findViewById(R.id.ChkAgree);

        text2 = (TextView) findViewById(R.id.Text2);
        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);
        rdoDog = (RadioButton) findViewById(R.id.Rdodog);
        rdoCat = (RadioButton) findViewById(R.id.Rdocat);
        rdoRabbit = (RadioButton) findViewById(R.id.Rdorabbit);

        btnOK = (Button) findViewById(R.id.BtnOK);
        imgPet = (ImageView) findViewById(R.id.ImgPet);

        LinearLayout ImgOptBox = (LinearLayout) findViewById(R.id.ImgOptBox);
        btnZoomIn = (Button) findViewById(R.id.BtnZoomIn);
        btnZoomOut = (Button) findViewById(R.id.BtnZoomOut);
        btnRotate = (Button) findViewById(R.id.BtnRotate);
        btnBrighten = (Button) findViewById(R.id.BtnBrighten);
        btnDarken = (Button) findViewById(R.id.BtnDarken);


        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkAgree.isChecked() == true) {
                    text2.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    btnOK.setVisibility(View.VISIBLE);
                    imgPet.setVisibility(View.VISIBLE);
                } else {
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int checkedRadioButtonId = rGroup1.getCheckedRadioButtonId();

                if (checkedRadioButtonId == R.id.Rdodog) {
                    loadImageFromDisk("/storage/emulated/0/Download/dog.png");
                    ImgOptBox.setVisibility(View.VISIBLE);
                } else if (checkedRadioButtonId == R.id.Rdocat) {
                    loadImageFromDisk("/storage/emulated/0/Download/cat.png");
                    ImgOptBox.setVisibility(View.VISIBLE);
                } else if (checkedRadioButtonId == R.id.Rdorabbit) {
                    loadImageFromDisk("/storage/emulated/0/Download/rabbit.png");
                    imgPet.setImageResource(R.drawable.rabbit);
                    ImgOptBox.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "동물 먼저 선택하세요", Toast.LENGTH_SHORT).show();
                }

//                onRequestPermissionsResult(100, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new int[]{PackageManager.PERMISSION_GRANTED});

            }
        });

        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale += 0.1f; // 확대
                imgPet.setScaleX(scale);
                imgPet.setScaleY(scale);
            }
        });

        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scale > 0.1f) {
                    scale -= 0.1f; // 축소
                }
                imgPet.setScaleX(scale);
                imgPet.setScaleY(scale);
            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle += 10f; // 10도씩 회전
                imgPet.setRotation(angle);
            }
        });

        btnBrighten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brightness += 0.1f; // 밝기 증가
                if (brightness > 2.0f) brightness = 2.0f; // 최대 밝기 제한
                imgPet.setColorFilter(Color.argb((int) (255 * brightness), 255, 255, 255));
            }
        });

        btnDarken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brightness -= 0.1f; // 밝기 감소
                if (brightness < 0.5f) brightness = 0.5f; // 최소 밝기 제한
                imgPet.setColorFilter(Color.argb((int) (255 * brightness), 255, 255, 255));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 이미지 로드 시도
                loadImageFromDisk(imagePath);
            } else {
                Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}