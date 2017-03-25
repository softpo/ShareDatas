package com.softpo.sharedatas;

import android.Manifest;
import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.R.attr.key;
import static android.R.attr.value;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //运行时权限，检查用户是否已经授权读取内存卡权限
        if(PackageManager.PERMISSION_GRANTED==ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
        }else {
            //没有该权限，申请
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
    }
    //申请权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 101:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"获取读取内存卡权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public void send(View v) {
        Intent sendData = null;
        //sd卡图片的根目录
        File pictures_root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //sd卡视频的根目录
        File video_root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        switch (v.getId()) {
            case R.id.txt:
                //发送文本数据
                sendData = new Intent(Intent.ACTION_SEND);
                sendData.putExtra(Intent.EXTRA_TEXT, "人类是悬挂在自己编制的意义之网上的动物");
                sendData.setType("text/plain");
                break;
            case R.id.img:
                //发送图片数据
                sendData = new Intent(Intent.ACTION_SEND);
                Uri img_uri = Uri.fromFile(new File(pictures_root,"huan.jpg"));
                sendData.putExtra(Intent.EXTRA_STREAM,img_uri);
                sendData.setType("image/*");//设置类别MIME_TYPE
                break;
            case R.id.imgs:
                //发送多媒体数据
                sendData = new Intent(Intent.ACTION_SEND_MULTIPLE);
                //图片资源
                img_uri = Uri.fromFile(new File(pictures_root,"huan.jpg"));
                //视频资源
                Uri video_uri = Uri.fromFile(new File(video_root,"takeashot.mp4"));
                ArrayList<Parcelable> values = new ArrayList<>();
                values.add(img_uri);
                values.add(video_uri);
                sendData.putParcelableArrayListExtra(Intent.EXTRA_STREAM,values);
                //设置类别MIME_TYPE
                sendData.setType("*/*");
                break;
            default:
                break;
        }
        this.startActivity(sendData);
    }

//    //布局中Button的点击事件
//    public void send(View view) {
//        //声明意图进行数据传递
//        Intent sendIntent = new Intent();
//        //设置隐式意图"发送"
//        sendIntent.setAction(Intent.ACTION_SEND);
//        //意图中存放数据
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//        //设置数据的类型
//        sendIntent.setType("text/plain");
//        //执行发送请求
//        startActivity(sendIntent);
//    }
}
