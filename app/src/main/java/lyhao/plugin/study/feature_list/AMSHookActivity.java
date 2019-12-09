package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.util.RefInvoke;

public class AMSHookActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        hookAMN();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void hookAMN(){
        Object gDefault = null;
        if (android.os.Build.VERSION.SDK_INT <= 25) {
            //获取AMN的gDefault单例gDefault，gDefault是静态的
            gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManagerNative", "gDefault");
        } else {
            //获取ActivityManager的单例IActivityManagerSingleton，他其实就是之前的gDefault
            gDefault = RefInvoke.getStaticFieldOjbect("android.app.ActivityManager", "IActivityManagerSingleton");
        }

        Object mInstance = RefInvoke.getFieldOjbect("android.util.Singleton", gDefault, "mInstance");
        try {
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
            if("startActivity".equals(method.getName())) {
                Log.e(TAG, method.getName());
                Log.e(TAG, "AMS Hooked");
                return method.invoke(mBase, args);
            }
            return method.invoke(mBase, args);
        }
    }
}
