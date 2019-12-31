package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.hooktest.LoadedApkClassLoaderHookHelper;

/**
 * Created by luyanhao on 2019/12/30.
 */
public class DynamicLoadePluginActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
