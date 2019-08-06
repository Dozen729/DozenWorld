package com.dozen.dozenworld.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.project.clean.CleanManager;
import com.dozen.dozenworld.project.clean.FileUtil;
import com.dozen.dozenworld.project.clean.JunkGroup;
import com.dozen.dozenworld.project.clean.JunkInfo;
import com.dozen.dozenworld.project.clean.JunkProcessInfo;
import com.dozen.dozenworld.rxpermissions2.RxPermissions;
import com.dozen.dozenworld.utils.T;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dozen on 19-8-5.
 * Describe:
 */
public class CleanActivity extends AppCompatActivity {

    private Button btnStartScan,btnStartClean,btnCancel;
    private TextView tvShow,tvResult,tvDetail;

    private RxPermissions permissions;
    private CleanManager cleanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);

        init();

        /*
        * 此清理不包括正在运行的APP程序和缓存内容,所以cleanRunning和cleanJunk无用
        * cleanManager.initData中cleanBigFile为true时,将会清理所以扫描到的大文件,请谨慎选择
        * */

        CleanManager.init(this);
        cleanManager=CleanManager.getInstance();
        //设置将要清理的内容,为true时清理
        cleanManager.initData(true,true,true,true,true,false);
        cleanManager.setScanListener(iScanListener);



        btnStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanManager.startScanTask();
            }
        });
        btnStartClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanManager.startCleanTask();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanManager.cancelScanTask();
            }
        });
    }

    private void init() {
        permissions=new RxPermissions(this);
        permissions.setLogging(true);

        permissions.request(Manifest.permission.UNINSTALL_SHORTCUT,Manifest.permission.INSTALL_SHORTCUT,Manifest.permission.GET_PACKAGE_SIZE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                T.showLongToast("good");
            }
        });

        btnStartScan=findViewById(R.id.btn_clean_scan);
        btnStartClean=findViewById(R.id.btn_clean_start);
        btnCancel=findViewById(R.id.btn_clean_cancel);

        tvShow=findViewById(R.id.tv_clean_path);
        tvResult=findViewById(R.id.tv_clean_log);
        tvDetail=findViewById(R.id.tv_clean_content);
    }

    CleanManager.IScanListener iScanListener=new CleanManager.IScanListener() {
        @Override
        public void startScan() {
            T.showLongToast("start");
        }

        @Override
        public void isOverScanFinish(ArrayList<JunkInfo> apkList, ArrayList<JunkInfo> logList, ArrayList<JunkInfo> tempList, ArrayList<JunkInfo> bigFileList) {

            tvShow.setText("");

            tvResult.setText("");
            showResult("apk总大小:"+getFilterJunkSize(apkList));
            showResult("log总大小:"+getFilterJunkSize(logList));
            showResult("temp总大小:"+getFilterJunkSize(tempList));
            showResult("big总大小:"+getFilterJunkSize(bigFileList));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void isAllScanFinish(JunkGroup junkGroup) {
            tvDetail.setText("清理的文件：");

            StringBuilder all= new StringBuilder();

            long totalSize=0L;

            List<JunkProcessInfo> listInfo;
            for (int i = 2; i < 6; i++) {
                listInfo=junkGroup.getJunkList(i);
                for (int j = 0; j < listInfo.size(); j++) {
                    JunkProcessInfo junk=listInfo.get(j);
                    if (junk.isCheck()){
                        String detail=junk.getJunkInfo().getName()+"(size:"+FileUtil.formatFileSize(junk.getJunkInfo().getSize())+")";
                        all.append("\n").append(detail);

                        totalSize+=junk.getJunkInfo().getSize();
                    }
                }
            }
            tvDetail.setText(tvDetail.getText()+"\n"+all);
            tvShow.setText("总大小:"+FileUtil.formatFileSize(totalSize));
        }

        @Override
        public void currentOverScanJunk(JunkInfo junkInfo) {
            Message message=new Message();
            message.obj=junkInfo.getPath();
            showPath.sendMessage(message);
        }

        @Override
        public void scanCancel() {
            T.showLongToast("scan cancel");
        }

        @Override
        public void cleanFail() {
            T.showLongToast("clean fail");
        }

        @Override
        public void cleanSuccess() {
            T.showLongToast("clean success");
        }
    };

    Handler showPath=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            tvShow.setText(msg.obj.toString());

            return false;
        }
    });

    @SuppressLint("SetTextI18n")
    private void showResult(String text){
        tvResult.setText(tvResult.getText()+"\n"+text);
    }

    private String getFilterJunkSize(ArrayList<JunkInfo> list) {

        long size = 0L;
        for (JunkInfo info : list) {
            size += info.getSize();
        }
        return FileUtil.formatFileSize(size).toString();
    }

}
