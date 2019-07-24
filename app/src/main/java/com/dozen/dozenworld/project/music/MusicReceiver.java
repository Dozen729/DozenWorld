package com.dozen.dozenworld.project.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dozen.dozenworld.activity.MusicActivity;

/**
 * Created by Dozen on 19-7-23.
 * Describe:
 */
public class MusicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ctrl_code=intent.getAction();


        assert ctrl_code != null;

        if (MusicActivity.iMusic==null){
            return;
        }

        switch (ctrl_code){
            case "last":
                MusicActivity.iMusic.lastSong();
                break;
            case "stop":
                MusicActivity.iMusic.pause();
                break;
            case "play":
                MusicActivity.iMusic.moveOn();
                break;
            case "next":
                MusicActivity.iMusic.nextSong();
                break;
            case "close":
                MusicActivity.iMusic.stop();
                break;
        }
//        T.showLongToast(ctrl_code);
    }
}
