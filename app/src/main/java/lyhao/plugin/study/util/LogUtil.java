package lyhao.plugin.study.util;

import android.util.Log;

/**
 * Created by luyanhao on 2019/12/19.
 */
public class LogUtil {
    public static boolean debugable = true;
    public static final String globalTAG = "plugin_study";
    public static void d(String TAG, String content){
        if(debugable) {
            Log.d(globalTAG + TAG, content);
        }
    }
}
