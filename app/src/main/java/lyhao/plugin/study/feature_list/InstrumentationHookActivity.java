package lyhao.plugin.study.feature_list;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import lyhao.plugin.study.R;
import lyhao.plugin.study.TestActivity;
import lyhao.plugin.study.util.RefInvoke;

public class InstrumentationHookActivity extends AppCompatActivity {

    final String TAG = InstrumentationHookActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lnstrumentation_hook);
        Instrumentation mInstrumentation =
                (Instrumentation) RefInvoke.getFieldOjbect(Activity.class, this, "mInstrumentation");
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldOjbect(Activity.class, "mInstrumentation", this, evilInstrumentation);
        TextView jumpToTest = findViewById(R.id.jumpToTest);
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
            Log.d(TAG, "xxx到此一游");
            Class[] p1 = {Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class};
            Object[] v1 = {who, contextThread, token, target, intent, requestCode, options};
            return (ActivityResult) RefInvoke.invokeMethod(Instrumentation.class, "execStartActivity", mBase, p1, v1);
        }
    }
}
