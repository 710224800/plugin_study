package lyhao.plugin.study.util;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import lyhao.plugin.study.UPFApplication;

/**
 * Created by luyanhao on 2019/12/19.
 */
public class Utils {
    public static final String TAG = Utils.class.getSimpleName();
    private static final String SDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//    public static final String savePath = SDCardPath + "/data/data/lyhao.plugin.study/files";//路径分隔符
    public static String savePath = null;

    /**
     * 复制assets下的apk到sd卡里
     * @param myContext
     * @param ASSETS_NAME 要复制的文件名
     */
    public static String extractAssets(Context myContext, String ASSETS_NAME) {
        if (sBaseDir == null) {
            // 目录为：/data/data/<package>/files/plugin/
            sBaseDir = UPFApplication.getContext().getFileStreamPath("plugin");
        }
        savePath = sBaseDir.getPath();
        LogUtil.d(TAG, "extractAssets");
        String filePath = savePath + "/" + ASSETS_NAME;
        LogUtil.d(TAG, "savePath=" + savePath);
        LogUtil.d(TAG, "fileFullPath=" + filePath);
        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (!(new File(filePath)).exists()) {
                InputStream is = myContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filePath);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 待加载插件经过opt优化之后存放odex得路径
     */
    public static File getPluginOptDexDir(String packageName) {
        return enforceDirExists(new File(getPluginBaseDir(packageName), "odex"));
    }

    /**
     * 插件得lib库路径, 这个demo里面没有用
     */
    public static File getPluginLibDir(String packageName) {
        return enforceDirExists(new File(getPluginBaseDir(packageName), "lib"));
    }

    // --------------------------------------------------------------------------
    private static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    private static File sBaseDir;

    // 需要加载得插件得基本目录 /data/data/<package>/files/plugin/
    private static File getPluginBaseDir(String packageName) {
        if (sBaseDir == null) {
            sBaseDir = UPFApplication.getContext().getFileStreamPath("plugin");
            enforceDirExists(sBaseDir);
        }
        return enforceDirExists(new File(sBaseDir, packageName));
    }

    private static synchronized File enforceDirExists(File sBaseDir) {
        if (!sBaseDir.exists()) {
            boolean ret = sBaseDir.mkdir();
            if (!ret) {
                throw new RuntimeException("create dir " + sBaseDir + "failed");
            }
        }
        return sBaseDir;
    }
}
