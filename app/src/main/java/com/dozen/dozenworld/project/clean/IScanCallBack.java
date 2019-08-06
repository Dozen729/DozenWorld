package com.dozen.dozenworld.project.clean;

import java.util.ArrayList;

public interface IScanCallBack {

    void onBegin();

    void onProgress(JunkInfo junkInfo);

    void onCancel();

    void onFinish(ArrayList<JunkInfo> apkList, ArrayList<JunkInfo> logList,
                  ArrayList<JunkInfo> tempList, ArrayList<JunkInfo> bigFileList);

    void onOverTime();
}