package com.dozen.dozenworld.project.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;

import com.blankj.utilcode.utils.AppUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hugo on 19-8-22.
 * Describe:
 */
public class SignModel {
    public static List<AppInfo> getAllPrograms(Context context) {

        List<AppInfo> appList = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        String appName = AppUtils.getAppName(context);

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(resolveIntent, 0);
        Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(packageManager));
        for (ResolveInfo info : resolveInfoList) {
            String name = info.loadLabel(packageManager).toString();
            if (name.equals(appName)) {
                continue;
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(name);
            appInfo.setAppIcon(info.activityInfo.loadIcon(packageManager));

            appInfo.setPackname(info.activityInfo.applicationInfo.packageName);
//            appInfo.setPackname(info.activityInfo.applicationInfo.name);

//            LogUtils.d(TAG, name + "===" + info.activityInfo.applicationInfo.packageName + "===" + info.activityInfo.applicationInfo.name);

            appList.add(appInfo);
        }
        return appList;
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static String getRawSignature(Context paramContext, String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            return "获取签名失败，包名为 null";
        }
        PackageManager localPackageManager = paramContext.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(paramString, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                return "信息为 null, 包名 = " + paramString;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            return "包名没有找到...";
        }
        Signature[] signatures= localPackageInfo.signatures;

//        return signatures[0].toCharsString();
        return signatureSHA256(signatures);
    }

    /**
     * SHA256
     */
    private static String signatureSHA256(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    // 如需要小写则把ABCDEF改成小写
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 进行转换
     */
    private static String toHexString(byte[] bData) {
        StringBuilder sb = new StringBuilder(bData.length * 2);
        for (int i = 0; i < bData.length; i++) {
            sb.append(HEX_DIGITS[(bData[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bData[i] & 0x0f]);
        }
        return sb.toString();
    }

    /*
    * 参考https://www.jianshu.com/p/61b20bb03252
    * */
}
