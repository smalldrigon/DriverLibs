package com.grgbanking.huitong.driver_libs.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/9/11 16:35
 * Email: 904430803@qq.com
 * Description:
 */
public class FileAssetsUtil {

    /**
     * Created by shenhua on 1/17/2017.
     * Email shenhuanet@126.com
     */


    private static FileAssetsUtil instance;
    private static final int SUCCESS = 1;
     private static final int SUCCESS2SD = 3;
    private static final int FAILED = 0;
     private static final int FAILED2SD = 2;
    private Context context;
    private FileOperateCallback callback;
    private FileOperateCallback callback2SD;
    private volatile boolean isSuccess;
    private volatile boolean isSuccessToData;
    private String errorStr;

    public static FileAssetsUtil getInstance(Context context) {
        if (instance == null)
            instance = new FileAssetsUtil(context);
        return instance;
    }

    private FileAssetsUtil(Context context) {
        this.context = context;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("gong","msg"+msg.what);

            switch (msg.what){
                case SUCCESS:
                    if (callback!=null) callback.onSuccess();
                    break;
                case SUCCESS2SD:
                    if (callback2SD!=null) callback2SD.onSuccess();
                    break;
                case FAILED:
                    if (callback!=null) callback.onFailed("复制到data失败");
                    break;
                case FAILED2SD:
                    if (callback2SD!=null) callback2SD.onFailed("复制到SD失败");

                    break;
            }

            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed(msg.obj.toString());
                }
            }
        }
    };

    public FileAssetsUtil copyAssetsToSD(final String srcPath, final String sdPath) {
        new Thread(() -> {
            copyAssetsToDst(context, srcPath, sdPath);
            if (isSuccess)
                handler.obtainMessage(SUCCESS2SD).sendToTarget();
            else
                handler.obtainMessage(FAILED2SD, errorStr).sendToTarget();
        }).start();
        return this;
    }

    public void setCallback2SD(FileOperateCallback callback2SD) {
        this.callback2SD = callback2SD;
    }

    public FileAssetsUtil copyAssetsToData(final String srcPath, final String sdPath) {
        new Thread(() -> {
            copyAssetsToData(context, srcPath, sdPath);
            if (isSuccessToData) {
                String cmd = "chmod -R 777 data/ca10";
                List commandList = new ArrayList<String>();
//               commandList.add(InstallSilent.COMMAND_SU);
                commandList.add(cmd);
                InstallSilent.execRootCommand(commandList, false, true);
                handler.obtainMessage(SUCCESS).sendToTarget();
            } else {
                handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    private void copyAssetsToData(Context context, String srcPath, String dir) {

        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getDataDirectory(), dir);
                if (!file.exists()) {
                    boolean result = file.mkdirs();
//                    Log.i("gong", "创建文件夹" + result);
                    if (!result){
                        String cmd = "mkdir data/ca10/";
                        String permission = "chmod 777 data/ca10";
                        List commandList = new ArrayList<String>();
//               commandList.add(InstallSilent.COMMAND_SU);
                        commandList.add(cmd);
                        commandList.add(permission);
                        InstallSilent.execRootCommand(commandList, false, true);
                    }

                }
                Log.i("gong", file.getAbsolutePath());
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToData(context, srcPath + File.separator + fileName, dir + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToData(context, fileName, dir + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getDataDirectory(), dir);
                if (outFile.exists()) {
//                    Log.i("gong", "文件存在" + outFile.getName());
                    isSuccessToData = true;
                    return;
                }
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccessToData = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccessToData = false;
        }
    }

    private void copyAssetsToDst(Context context, String srcPath, String dir) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dir + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dir + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory(), dir);
                if (outFile.exists()){
                    isSuccess = true;
                    return;
                }

                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

}


