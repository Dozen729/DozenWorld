package com.dozen.dozenworld.project.clean;

import android.content.Context;
import android.os.AsyncTask;

import com.dozen.dozenworld.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CleanManager {

    private static CleanManager sInstance;
    private OverScanTask mOverScanTask;
    private ArrayList<JunkInfo> mApkList;
    private ArrayList<JunkInfo> mLogList;
    private ArrayList<JunkInfo> mTempList;
    private ArrayList<JunkInfo> mBigFileList;
    private JunkGroup mJunkGroup;
    private boolean mIsOverScanFinish;
    private IScanListener mScanListener;
    private Context mContext;

    private List<MultiItemEntity> mData;

    private CleanManager(Context context) {

        mContext = context.getApplicationContext();
        mApkList = new ArrayList<>();
        mLogList = new ArrayList<>();
        mTempList = new ArrayList<>();
        mBigFileList = new ArrayList<>();
        mJunkGroup = new JunkGroup();
    }

    public static void init(Context context) {

        if (sInstance == null) {
            synchronized (CleanManager.class) {
                if (sInstance == null) {
                    sInstance = new CleanManager(context);
                }
            }
        }
    }

    public static CleanManager getInstance() {

        if (sInstance == null) {
            throw new IllegalStateException("You must be init CleanManager first");
        }
        return sInstance;
    }

    public void startScanTask() {

        if (mScanListener != null) {
            mScanListener.startScan();
        }

        mOverScanTask = new OverScanTask(new IScanCallBack() {
            @Override
            public void onBegin() {
                mIsOverScanFinish = false;
            }

            @Override
            public void onProgress(JunkInfo junkInfo) {
                if (mScanListener != null) {
                    mScanListener.currentOverScanJunk(junkInfo);
                }
            }

            @Override
            public void onCancel() {
                mScanListener.scanCancel();
            }

            @Override
            public void onFinish(ArrayList<JunkInfo> apkList, ArrayList<JunkInfo> logList, ArrayList<JunkInfo> tempList, ArrayList<JunkInfo> bigFileList) {
                mIsOverScanFinish = true;
                mApkList = apkList;
                mLogList = logList;
                mTempList = tempList;
                mBigFileList = bigFileList;

                if (mScanListener != null) {
                    mScanListener.isOverScanFinish(mApkList, mLogList, mTempList, mBigFileList);
                    checkAllScanFinish();
                }
            }

            @Override
            public void onOverTime() {
                cancelScanTask();
                mIsOverScanFinish = true;
                checkAllScanFinish();
            }
        });
        mOverScanTask.execute();
    }

    public void startCleanTask() {

        if (mJunkGroup == null || !mIsOverScanFinish) {
            return;
        }

        for (int i = 2; i < 6; i++) {
            JunkType junkType = (JunkType) mData.get(i);
            ArrayList<JunkProcessInfo> infos = mJunkGroup.getJunkList(i);

            for (int j = 0; j < infos.size(); j++) {
                junkType.addSubItem(infos.get(j));
            }
            mData.set(i, junkType);
        }

        List<String> junkList = getJunkList(mData);

        Observable<Boolean> booleanObservable = cleanJunksUsingObservable(junkList);
        booleanObservable.subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                mScanListener.cleanSuccess();
                mIsOverScanFinish = false;
            }

            @Override
            public void onError(Throwable e) {
                mScanListener.cleanFail();
                mIsOverScanFinish = false;
            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });


    }

    public void initData(boolean cleanRunning, boolean cleanJunk, boolean cleanUnUsefulApk, boolean cleanTempFile, boolean cleanLog, boolean cleanBigFile) {
        ArrayList<MultiItemEntity> list = new ArrayList<>();
        int title[] = {R.string.running_app, R.string.cache_junk, R.string.unuseful_apk,
                R.string.temp_file, R.string.log, R.string.big_file};
        int resourceId[] = {R.drawable.icon_process_white_24dp, R.drawable.icon_cache_white_24dp,
                R.drawable.icon_apk_white_24dp, R.drawable.icon_temp_file_white_24dp,
                R.drawable.icon_log_white_24dp, R.drawable.icon_big_file_white_24dp};
        for (int i = 0; i < 6; i++) {
            JunkType junkType = new JunkType();
            junkType.setTitle(mContext.getString(title[i]))
                    .setCheck(true)
                    .setIconResourceId(resourceId[i])
                    .setTotalSize("")
                    .setProgressVisible(true);
            if (JunkType.BIG_FILE == i || JunkType.CACHE == i) {
                junkType.setCheck(false);
            }
            switch (i) {
                case JunkType.PROCESS:
                    junkType.setCheck(cleanRunning);
                    break;
                case JunkType.CACHE:
                    junkType.setCheck(cleanJunk);
                    break;
                case JunkType.APK:
                    junkType.setCheck(cleanUnUsefulApk);
                    break;
                case JunkType.TEMP:
                    junkType.setCheck(cleanTempFile);
                    break;
                case JunkType.LOG:
                    junkType.setCheck(cleanLog);
                    break;
                case JunkType.BIG_FILE:
                    junkType.setCheck(cleanBigFile);
                    break;
            }

            list.add(junkType);
        }
        mData = list;
    }

    private List<String> getJunkList(List<MultiItemEntity> list) {
        List<String> tempList = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
            tempList.addAll(getJunkTypeList(list.get(i)));
        }

        return tempList;
    }

    private List<String> getJunkTypeList(MultiItemEntity entity) {
        List<JunkProcessInfo> appCacheList = ((JunkType) entity).getSubItems();
        List<String> tempList = new ArrayList<>();

        //appCacheList可能为空
        if (appCacheList != null) {
            for (JunkProcessInfo info : appCacheList) {
                if (info.isCheck()) {
                    tempList.add(info.getJunkInfo().getPath());
                }
            }
        }
        return tempList;
    }

    private Observable<Boolean> cleanJunksUsingObservable(final List<String> junkList) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                subscriber.onNext(cleanJunks(junkList));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private boolean cleanJunks(List<String> junkList) {

        for (int i = 0; i < junkList.size(); i++) {
            try {
                FileUtil.deleteTarget(junkList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void setScanListener(IScanListener listener) {
        this.mScanListener = listener;
    }

    private void checkAllScanFinish() {
        if (mIsOverScanFinish) {
            mJunkGroup.setApkList(getJunkProcessList(mApkList, JunkType.APK))
                    .setLogList(getJunkProcessList(mLogList, JunkType.LOG))
                    .setTempList(getJunkProcessList(mTempList, JunkType.TEMP))
                    .setBigFileList(getJunkProcessList(mBigFileList, JunkType.BIG_FILE));
            mScanListener.isAllScanFinish(mJunkGroup);
        }
    }

    private ArrayList<JunkProcessInfo> getJunkProcessList(ArrayList<JunkInfo> list, int type) {

        ArrayList<JunkProcessInfo> tempList = new ArrayList<>();
        for (JunkInfo junkInfo : list) {
            for (int i = 0; i < junkInfo.getChildren().size(); i++) {
                JunkInfo info = junkInfo.getChildren().get(i);
                JunkProcessInfo junkProcessInfo = new JunkProcessInfo(info, type);
                junkProcessInfo.setCheck(info.isCheck());
                tempList.add(junkProcessInfo);
            }
        }
        return tempList;
    }

    public void cancelScanTask() {
        //判断当前的异步任务是否为空，并且判断当前的异步任务的状态是否是运行状态{RUNNING(运行),PENDING(准备),FINISHED(完成)}
        if (mOverScanTask != null && mOverScanTask.getStatus() == AsyncTask.Status.RUNNING) {
            /**
             *cancel(true) 取消当前的异步任务，传入的true,表示当中断异步任务时继续已经运行的线程的操作，
             *但是为了线程的安全一般为让它继续设为true
             * */
            mOverScanTask.cancel(true);
        }
    }

    public interface IScanListener {

        void startScan();

        void isOverScanFinish(ArrayList<JunkInfo> apkList, ArrayList<JunkInfo> logList, ArrayList<JunkInfo> tempList, ArrayList<JunkInfo> bigFileList);

        void isAllScanFinish(JunkGroup junkGroup);

        void currentOverScanJunk(JunkInfo junkInfo);

        void scanCancel();

        void cleanFail();

        void cleanSuccess();

    }
}
