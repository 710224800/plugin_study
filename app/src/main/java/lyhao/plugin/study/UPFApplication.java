package lyhao.plugin.study;

import android.app.Application;
import android.content.Context;

/**
 * Created by luyanhao on 2019/12/31.
 */
public class UPFApplication extends Application {
    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
