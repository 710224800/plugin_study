package lyhao.plugin.study.hooktest;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import lyhao.plugin.study.util.RefInvoke;

/**
 * Created by luyanhao on 2019/11/15.
 */
public class HookHelper {
    public static final String TAG = HookHelper.class.getSimpleName();
    public static void hookActivityManager() {
        try {
            //获取M侧的gDefault 单例gDefaul t, gDefaul t 是静态约
            Object gDefault = null;
            if (android.os.Build.VERSION.SDK_INT <= 25) {
                //获取AMN的gDefault单例gDefault，gDefault是静态的
                gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManagerNative", "gDefault");
            } else {
                //获取ActivityManager的单例IActivityManagerSingleton，他其实就是之前的gDefault
                gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManager", "IActivityManagerSingleton");
            }
            Object rawIActivityManager = RefInvoke.getFieldOjbect(
                    "android.util.Singleton", gDefault, "mInstance");
            Class<?> iActivityManagerinterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?> []{iActivityManagerinterface},
                    new HookHandler(rawIActivityManager)) ;

            RefInvoke.setFieldOjbect("android.util.Singleton",
                    "mInstance", gDefault, proxy);
            Log.d(TAG, "1111111111111111111111111111111111111111111111");
        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
    }

    static class HookHandler implements InvocationHandler {
        Object mBase;

        public HookHandler(Object base){
            this.mBase = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "hey,baby; you are hooked !!");
            Log.d(TAG, "”method :" + method.getName() + "called with args :"
                    + Arrays.toString (args));
            return method.invoke(mBase, args);
        }
    }

}