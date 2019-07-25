package com.dozen.dozenworld.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.project.suspend.SuspendService;
import com.dozen.dozenworld.utils.T;

/**
 * Created by Dozen on 19-7-25.
 * Describe:
 */
public class SuspendActivity extends AppCompatActivity {

    private boolean isGet=false;
    private Intent suspendIntent;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_suspend);

        Button btnStart=findViewById(R.id.btn_suspend_start);
        btnStart.setOnClickListener(btnListener);

        Button btnClose=findViewById(R.id.btn_suspend_close);
        btnClose.setOnClickListener(btnListener);

        if (openDisplay()){
            isGet=true;
        }
    }

    private boolean openDisplay(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                T.showLongToast(getResources().getString(R.string.app_name)+"---需要开启悬浮窗权限,才可以正常运行,请务必开启!!!");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 3);
                return false;
            }else {
                isGet=true;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3){
            if (resultCode==RESULT_OK){
                isGet=true;
            }else {
                isGet=false;
            }
        }
    }

    private View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (suspendIntent==null){
                if (isGet){
                    suspendIntent=new Intent(SuspendActivity.this,SuspendService.class);
                }else {
                    openDisplay();
                    return;
                }
            }

            switch (v.getId()){
                case R.id.btn_suspend_start:
                    startService(suspendIntent);
//                    bindService(suspendIntent,serviceConnection,Context.BIND_AUTO_CREATE);
                    break;
                case R.id.btn_suspend_close:
//                    unbindService(serviceConnection);
                    stopService(suspendIntent);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
