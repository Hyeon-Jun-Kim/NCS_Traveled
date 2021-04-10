package com.hanshin.ncs_travled;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BT_CreateActivity extends Activity {
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    ArrayList<Uri> videoList = new ArrayList<Uri>();
    BT_GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_create);

        //메인페이지 버튼의정
        Button btnPhotoBookInfo = findViewById(R.id.btnPhotoBookInfo);
        Button btnPhotoBookCover = findViewById(R.id.btnPhotoBookCover);
        Button btnPhotoBookSave = findViewById(R.id.btnPhotoBookSave);
        Button btnPhotoBookPageCreate = findViewById(R.id.btnPhotoBookPageCreate);
        Button btnPhotoBookPageDelete = findViewById(R.id.btnPhotoBookPageDelete);

        //그리드뷰 + 어댑터
        GridView gridView = findViewById(R.id.gridview);
        adapter = new BT_GridViewAdapter(this, imageList, videoList);
        gridView.setAdapter(adapter);

        //포토북생성페이지에 정보버튼안에 대화상자 속성 정의
        LinearLayout dialogView;
        //포토북생성페이지에 정보 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout dialogView = (LinearLayout) View.inflate(BT_CreateActivity.this, R.layout.dialog_photobookinfo, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_CreateActivity.this);
                dlg.setTitle(" 포토북 정보");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_info_24);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 버튼 누를시 이벤트 작성하기


                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        //포토북생성페이지에 표지 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout dialogView = (LinearLayout) View.inflate(BT_CreateActivity.this, R.layout.dialog_photobookcover, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(BT_CreateActivity.this);
                dlg.setTitle(" 포토북 표지");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.ic_baseline_menu_book_24);


                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 버튼 누를시 이벤트 작성하기


                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        //포토북생성페이지에 페이지 추가 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookPageCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BT_GridViewAdapter의 add메서드 실행

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                startActivityForResult(intent, 1000);
                adapter.add(imageList);
                adapter.notifyDataSetChanged();
            }
        });
        //포토북생성페이지에 페이지 삭제 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookPageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BT_GridViewAdapter의 delete메서드 실행
                adapter.delete();
                //삭제한 내용 보여주기
                adapter.notifyDataSetChanged();
            }
        });
        //포토북생성페이지에 저장 버튼을 클릭할 때 이벤트 작성
        btnPhotoBookSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    //갤러리 생성하기 필요한 메서드
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Uri imagePath = data.getData();

            adapter.notifyDataSetChanged();
            if (imagePath.toString().contains("image")) {
                //갤러리에서 이미지 경로 받아와서 리스트에 추가하기
                imageList.add(imagePath);
            } else  if (imagePath.toString().contains("video")) {
                //갤러리에서 비디오 경로 받아와서 리스트에 추가하기
                videoList.add(imagePath);
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}