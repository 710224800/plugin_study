package lyhao.plugin.study.hooktest;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lyhao.plugin.study.NoRegisterActivity;
import lyhao.plugin.study.TestActivity;
import lyhao.plugin.study.util.RefInvoke;

/**
 * Created by luyanhao on 2019/12/13.
 */
public class AMSHookHelper {
    public static final String TAG = AMSHookHelper.class.getSimpleName();
    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    /**
     * Hook AMS
     * 把真正要启动的Activity临时替换成Manifest中声明的替身Activity，进而骗过AMS
     */

    public void hookAMN() {
        Object gDefault = null;
        if (android.os.Build.VERSION.SDK_INT <= 25) {
            //获取AMN的gDefault单例gDefault，gDefault是静态的
            gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManagerNative", "gDefault");
        } else {
            //获取ActivityManager的单例IActivityManagerSingleton，他其实就是之前的gDefault
            gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManager", "IActivityManagerSingleton");
        }

        // gDefault是一个android.util.Singleton<T>对象; 我们取出这个单例里面的 mInstance 字段
        Object mInstance = RefInvoke.getFieldOjbect("android.util.Singleton", gDefault, "mInstance");

        try {
            // 创建一个mInstance 的代理对象MockClassl，然后替换这个字段，让我们的代理对象帮忙千活
            Class<?> classB2Interface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{classB2Interface}, new MockClass1(mInstance));
            RefInvoke.setFieldOjbect("android.util.Singleton", gDefault, "mInstance", proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    class MockClass1 implements InvocationHandler {

        Object mBase;

        public MockClass1(Object mBase){
            this.mBase = mBase;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, "method=" + method.getName());

            if("startActivity".equals(method.getName())){
                //只拦截此方法，替换参数，任你所为，甚至替换原始Activity启动别的Activity偷梁换柱
                //找到参数里面的第一个Intent对象
                Intent raw;
                int index = 0;
                for(int i=0; i<args.length; i++){
                    if(args[i] instanceof Intent){
                        index = i;
                        break;
                    }
                }
                raw = (Intent) args[index];
                Intent newIntent = new Intent();

                //先获取包名
                String stubPackage = raw.getComponent().getPackageName();

                Log.d(TAG, "packagename = " + stubPackage);

                ComponentName componentName = new ComponentName(stubPackage, TestActivity.class.getName());

                newIntent.setComponent(componentName);
                //把原始要启动的Activity先存越来
                if(raw.getExtras() != null) {
                    newIntent.putExtras(raw.getExtras());//这里要把extras设置进去
                }
                newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, raw);

                args[index] = newIntent;//替换

                Log.d(TAG, "欺骗 AMS hook success");
                return method.invoke(mBase, args);
            }

            return method.invoke(mBase, args);
        }
    }

    public void hookInstrumentation(){
        Object currentActivityThread = RefInvoke.getStaticFieldOjbect(
                "android.app.ActivityThread", "sCurrentActivityThread");
        Instrumentation mInstrumentation = (Instrumentation) RefInvoke.getFieldOjbect(
                "android.app.ActivityThread", currentActivityThread, "mInstrumentation");
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldOjbect(
                "android.app.ActivityThread", currentActivityThread, "mInstrumentation", evilInstrumentation);
    }

    class EvilInstrumentation extends Instrumentation{
        Instrumentation mBase;
        public EvilInstrumentation(Instrumentation mBase){
            this.mBase = mBase;
        }

        public Activity newActivity(ClassLoader cl, String className, Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
            Log.d(TAG, "EvilInstrumentation newActivity hooked");
            Intent rawIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
            if(rawIntent == null){
                return mBase.newActivity(cl, className, intent);
            }
            String newClassName = rawIntent.getComponent().getClassName();
            Log.d(TAG, "newClassName=" + newClassName);
            return mBase.newActivity(cl, newClassName, rawIntent);
        }
    }
}
