package lyhao.plugin.study.feature_list;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.TestActivity;
import lyhao.plugin.study.util.RefInvoke;

public class InstrumentationHookActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instrumentation mInstrumentation =
                (Instrumentation) RefInvoke.getFieldOjbect(Activity.class, this, "mInstrumentation");
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldOjbect(Activity.class, this, "mInstrumentation", evilInstrumentation);
        jumpToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstrumentationHookActivity.this, TestActivity.class));
            }
        });
    }

    class EvilInstrumentation extends Instrumentation {
        Instrumentation mBase;

        EvilInstrumentation(Instrumentation mBase){
            this.mBase = mBase;
        }

        public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                                Intent intent, int requestCode, Bundle options){
            Log.e(TAG, "Instrumentation Hooked");
            Class[] p1 = {Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class};
            Object[] v1 = {who, contextThread, token, target, intent, requestCode, options};
            return (ActivityResult) RefInvoke.invokeMethod(Instrumentation.class, "execStartActivity", mBase, p1, v1);
        }
    }
}
