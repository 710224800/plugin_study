package lyhao.plugin.study.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by luyanhao on 2019/12/19.
 */
public class Utils {
    public static final String TAG = Utils.class.getSimpleName();
    private static final String SDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String savePath = SDCardPath + "/data/data/lyhao.plugin.study/files";//路径分隔符

    /**
     * @param myContext
     * @param ASSETS_NAME 要复制的文件名
     */
    public static String extractAssets(Context myContext, String ASSETS_NAME) {
        LogUtil.d(TAG, "extractAssets");
        String filePath = savePath + "/" + ASSETS_NAME;
        LogUtil.d(TAG, "filename=" + filePath);
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
}
