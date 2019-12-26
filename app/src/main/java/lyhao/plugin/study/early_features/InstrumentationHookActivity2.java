package lyhao.plugin.study.early_features;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.TestActivity;
import lyhao.plugin.study.util.RefInvoke;

public class InstrumentationHookActivity2 extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Object currentActivityThread = RefInvoke.getStaticFieldOjbect(
                "android.app.ActivityThread", "sCurrentActivityThread");
        Instrumentation mInstrumentation = (Instrumentation) RefInvoke.getFieldOjbect(
                        "android.app.ActivityThread", currentActivityThread, "mInstrumentation");
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldOjbect(
                "android.app.ActivityThread", currentActivityThread, "mInstrumentation", evilInstrumentation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instrumentation mInstrumentation =
                (Instrumentation) RefInvoke.getFieldOjbect(Activity.class, this, "mInstrumentation");
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldOjbect(Activity.class, this, "mInstrumentation", evilInstrumentation);
    }

    class EvilInstrumentation extends Instrumentation {
        Instrumentation mBase;

        EvilInstrumentation(Instrumentation mBase){
            this.mBase = mBase;
        }

        public Activity newActivity(ClassLoader cl, String className, Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
            Log.d(TAG, "Instrumentation newActivity hooked");
            return mBase.newActivity(cl, className, intent);
        }

        public void callActivityOnCreate(Activity activity, Bundle icicle){
            Log.d(TAG, "Instrumentation callActivityOnCreate hooked");
            Class[] p1 = {Activity.class, Bundle.class};
            Object[] v1 = {activity, icicle};
            RefInvoke.invokeMethod(Instrumentation.class, mBase, "callActivityOnCreate", p1, v1);
        }
    }
}
