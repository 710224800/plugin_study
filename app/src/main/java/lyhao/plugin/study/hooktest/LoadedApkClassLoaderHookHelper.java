package lyhao.plugin.study.hooktest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lyhao.plugin.study.util.LogUtil;
import lyhao.plugin.study.util.RefInvoke;

/**
 * Created by luyanhao on 2019/12/30.
 */
public class LoadedApkClassLoaderHookHelper {
    public static final String TAG = LoadedApkClassLoaderHookHelper.class.getSimpleName();
    public static Map<String, Object> sLoadedApk = new HashMap<>();

    public static void hookLoadedApkInActivityThread(File apkFile){
        //先获取到当前的ActivityThread对象
        Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
        Map mPackages = (Map) RefInvoke.getFieldObject(currentActivityThread, "mPackages");
    }
}
