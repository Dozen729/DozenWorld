package com.dozen.dozenworld.project.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.dozen.dozenworld.utils.T;

import java.util.List;

/**
 * Created by Dozen on 19-7-23.
 * Describe:
 */
public class PlayerService extends Service {


    private MediaPlayer mp;
    private List<Song> songs;
    private int songItemPos = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        songItemPos = 5;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        Bundle extras = intent.getExtras();
        assert extras != null;
        songs = (List<Song>) extras.getSerializable("music");

        if (songs != null && songs.size() != 0) {
            mp = new MediaPlayer();
            play(songItemPos);
            return new MusicListener();
        } else {
            T.showLongToast("音乐列表为空");
            return null;
        }


    }

    private void play(int songItemPos) {
        T.showLongToast(songItemPos + "");
        if (songItemPos >= songs.size()) {
            songItemPos = 0;
        }

        if (mp == null) {
            mp = new MediaPlayer();
        }

        Song currentSong = songs.get(songItemPos);
//        Uri musicTableForSD = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Uri uri = Uri.withAppendedPath(musicTableForSD,
//                "/" + currentSong.getId());//初始化mp对象
        try {
            mp.reset();
            mp.setDataSource(currentSong.getPath());
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            T.showLongToast("e:" + e);
        }


    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MusicListener extends Binder implements IMusic {

        @Override
        public void moveOn() {
            T.showLongToast("play");
            if (mp != null) {
                mp.start();
            } else {
                play(songItemPos);
            }
        }

        @Override
        public void pause() {
            T.showLongToast("pause");
            if (mp != null) {
                mp.pause();
            }
        }

        @Override
        public void stop() {
            T.showLongToast("stop");
            if (mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
        }

        @Override
        public void nextSong() {
            T.showLongToast("next");
            if (mp != null) {
                mp.stop();
                play(++songItemPos);
            }
        }

        @Override
        public void lastSong() {
            T.showLongToast("last");
            if (mp != null) {
                mp.stop();
                play(--songItemPos);
            }
        }
    }
}
