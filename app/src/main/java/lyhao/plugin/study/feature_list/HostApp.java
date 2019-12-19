package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import lyhao.plugin.study.R;
import lyhao.plugin.study.util.LogUtil;
import lyhao.plugin.study.util.Utils;

/**
 * Created by luyanhao on 2019/12/17.
 */
public class HostApp extends AppCompatActivity {
    public static final String TAG = HostApp.class.getSimpleName();
    private String filePath;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        filePath = Utils.extractAssets(newBase, "app-debug.apk");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        File fileRelease = getDir("dex", 0);
        ClassLoader classLoader = new DexClassLoader(filePath, fileRelease.getAbsolutePath(), null, getClassLoader());

        try {
            Class mLoadClassBean = classLoader.loadClass("lyhao.plugin.plugin1.TestBean");
            Object beanObject = mLoadClassBean.newInstance();
            Method getNameMethod = mLoadClassBean.getMethod("getName");
            getNameMethod.setAccessible(true);
            String name = (String) getNameMethod.invoke(beanObject);
            LogUtil.d(TAG, "name = " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
