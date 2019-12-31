package lyhao.plugin.study.early_features;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.Log;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.util.RefInvoke;

/**
 * Created by luyanhao on 2019/12/4.
 */
public class Hook_mHActivity extends BaseActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        // 先获取到当前的ActivityThread 对象
        Object currentActivityThread = RefInvoke.getStaticFieldOjbect("android.app.ActivityThread",
                "sCurrentActivityThread");
        // 由于ActivityThread 一个进程只有一个， 我们获取这个对象的mH
        Handler mH = (Handler) RefInvoke.getFieldObject("android.app.ActivityThread",
                currentActivityThread, "mH");
        // 把Handler 的mCallback 字段， 替换为new MockClass2(mH)
        RefInvoke.setFieldObject(Handler.class, mH, "mCallback", new MockClass2(mH));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class MockClass2 implements Handler.Callback {
        Handler mBase;

        public MockClass2(Handler mBase){
            this.mBase = mBase;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, msg.what + "");
            switch(msg.what){
                //ActivityThread里面”LAUNCH ACTIVITY ”这个字段的值是100
                //本来使用反射的方式获取最好，这里为了简便直接使用硬编码
                case 100:
                    handleLaunchActivity(msg);
                    break;
            }
            mBase.handleMessage(msg);
            return true;
        }

        private void handleLaunchActivity(Message msg) {
            //这里简单起见， 直接取出TargetActivity;
            Object obj = msg.obj;
            Log.d(TAG, obj.toString());
        }
    }
}
