package com.dozen.dozenworld.project.suspend;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;

import com.dozen.dozenworld.utils.L;
import com.dozen.dozenworld.utils.T;

/**
 * Created by Dozen on 19-7-25.
 * Describe:
 */
public class SuspendService extends Service {

    private LogoSuspend logoSuspend;

    @Override
    public void onCreate() {
        super.onCreate();
        if (logoSuspend == null) {
            logoSuspend = new LogoSuspend(this);
        }
        logoSuspend.showSuspend(SuspensionCache.getSuspendSize(), false);//从缓存中提取上一次显示的位置
        logoSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理单击事件
                T.showLongToast("click");
            }
        });
        L.d("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.d("onBind");
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.d("OnUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        L.d("onDestroy");
        if (logoSuspend!=null){
            logoSuspend.dismissSuspend();
            logoSuspend=null;
        }
        super.onDestroy();
    }
}
