package lyhao.plugin.study.hooktest;

import android.content.pm.ApplicationInfo;

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

    public static void hookLoadedApkInActivityThread(File apkFile) throws ClassNotFoundException {
        //先获取到当前的ActivityThread对象
        Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
        //获取 mPackages 这个成员变量， 这里缓存了dex包的信息
        Map mPackages = (Map) RefInvoke.getFieldObject(currentActivityThread, "mPackages");

        //准备两个参数
        //android.content.res.CompatibilityInfo
        Object defaultCompatibilityInfo = RefInvoke.getStaticFieldObject("android.content.res.CompatibilityInfo",
                "DEFAULT_COMPATIBILITY_INFO");
        //从Apk中取得ApplicationInfo信息
        ApplicationInfo applicationInfo = generateApplicationInfo(apkFile);

        //调用ActivityThread的 getPackageInfoNoCheck 方法loadApk，上面得到的两个数据都是用来做参数的
        Class[] p1 = {ApplicationInfo.class, Class.forName("android.content.res.CompatibilityInfo")};
        Object[] v1 = {applicationInfo, defaultCompatibilityInfo};

        Object loadedApk = RefInvoke.invokeInstanceMethod(currentActivityThread, "getPackageInfoNoCheck", p1, v1);

        //为插件造一个新的classLoader
//        String odexPath =
    }

    public static ApplicationInfo generateApplicationInfo(File apkFile){
        ApplicationInfo applicationInfo = null;

        return applicationInfo;
    }
}
