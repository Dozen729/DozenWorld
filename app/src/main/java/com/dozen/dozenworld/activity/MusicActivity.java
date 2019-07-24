package com.dozen.dozenworld.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.dozen.dozenworld.R;
import com.dozen.dozenworld.project.music.IMusic;
import com.dozen.dozenworld.project.music.MusicReceiver;
import com.dozen.dozenworld.project.music.PlayerService;
import com.dozen.dozenworld.project.music.Song;
import com.dozen.dozenworld.rxpermissions2.RxPermissions;
import com.dozen.dozenworld.utils.T;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dozen on 19-7-23.
 * Describe:
 */
public class MusicActivity extends AppCompatActivity {

    private NotificationManager nm;
    public static int NOTIFICATION_ID=1111111;
    private MusicReceiver musicReceiver;
    public static IMusic iMusic;
    private PlayerService playerService;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxPermissions=new RxPermissions(this);
        rxPermissions.setLogging(true);
        setContentView(R.layout.activity_music);


        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
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

            }
        });

        Button btnStart=findViewById(R.id.btn_start);
        btnStart.setOnClickListener(btnListener);
        Button btnBind=findViewById(R.id.btn_bind);
        btnBind.setOnClickListener(btnListener);
        Button btnUnBind=findViewById(R.id.btn_un_bind);
        btnUnBind.setOnClickListener(btnListener);
        Button btnStop=findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(btnListener);

        nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_start:
                    initNotificationBar();
                    initReceiver();
                    playerService=new PlayerService();
                    break;
                case R.id.btn_bind:
                    List<Song> list=getMusic(getBaseContext());
                    if (list==null||list.size()==0||list.isEmpty()){
                        T.showLongToast("音乐列表为空");
                        return;
                    }

                    if (iMusic!=null){
                        return;
                    }

                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("music", (Serializable) list);
                    intent.putExtras(bundle);

                    iMusic= (IMusic) playerService.onBind(intent);
                    break;
                case R.id.btn_un_bind:
                    if (playerService!=null){
                        playerService.onUnbind(null);
                    }
                    break;
                case R.id.btn_stop:
                    if (playerService!=null){
                        playerService.onDestroy();
                        playerService=null;
                    }
                    nm.cancel(NOTIFICATION_ID);
                    if (musicReceiver!=null){
                        getBaseContext().unregisterReceiver(musicReceiver);
                    }
                    break;
                    default:break;
            }
        }
    };

    private void initReceiver() {
        musicReceiver=new MusicReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        getBaseContext().registerReceiver(musicReceiver,homeFilter);
    }


    private void initNotificationBar() {
        Notification notification=new Notification();

        notification.icon=R.drawable.icon;
        notification.iconLevel=R.drawable.icon;

        RemoteViews remoteView=new RemoteViews(getPackageName(),R.layout.notification_music);
        notification.contentView=remoteView;

        Intent intentLast=new Intent("last");
        PendingIntent pIntentLast=PendingIntent.getBroadcast(this,0,intentLast,0);
        remoteView.setOnClickPendingIntent(R.id.btn_nm_last,pIntentLast);

        Intent intentStop=new Intent("stop");
        PendingIntent pIntentStop=PendingIntent.getBroadcast(this,1,intentStop,0);
        remoteView.setOnClickPendingIntent(R.id.btn_nm_stop,pIntentStop);

        Intent intentPlay=new Intent("play");
        PendingIntent pIntentPlay=PendingIntent.getBroadcast(this,2,intentPlay,0);
        remoteView.setOnClickPendingIntent(R.id.btn_nm_play,pIntentPlay);

        Intent intentNext=new Intent("next");
        PendingIntent pIntentNext=PendingIntent.getBroadcast(this,3,intentNext,0);
        remoteView.setOnClickPendingIntent(R.id.btn_nm_next,pIntentNext);

        Intent intentClose=new Intent("close");
        PendingIntent pIntentClose=PendingIntent.getBroadcast(this,4,intentClose,0);
        remoteView.setOnClickPendingIntent(R.id.btn_nm_close,pIntentClose);

        notification.flags=Notification.FLAG_NO_CLEAR;

        nm.notify(NOTIFICATION_ID,notification);

    }

    private List<Song> getMusic(Context context) {

        List<Song> list = new ArrayList<>();


//        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI
//                , null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        //Environment.getExternalStorageDirectory().getAbsolutePath()
        //MediaStore.Audio.Media.EXTERNAL_CONTENT_URI


        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                //list.add(song);
                //把歌曲名字和歌手切割开
                //song.setName(name);
                song.setSinger(singer);
                song.setPath(path);
                song.setDuration(duration);
                song.setSize(size);
                song.setId(id);
                song.setAlbumId(albumId);
                if (size > 1000 * 800) {
                    if (name.contains("-")) {
                        String[] str = name.split("-");
                        singer = str[0];
                        song.setSinger(singer);
                        name = str[1];
                        song.setName(name);
                    } else {
                        song.setName(name);
                    }
                    list.add(song);
                }
            }
        }
        assert cursor != null;
        cursor.close();
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
