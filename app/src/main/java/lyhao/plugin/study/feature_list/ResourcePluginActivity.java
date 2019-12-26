package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import lyhao.plugin.mypluginlibrary.IDynamic;
import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.util.LogUtil;
import lyhao.plugin.study.util.Utils;

/**
 * Created by luyanhao on 2019/12/24.
 */
public class ResourcePluginActivity extends BaseActivity {
    public static final String TAG = ResourcePluginActivity.class.getSimpleName();
    private String apkFilePath;
    private File fileRelease;
    private DexClassLoader dexClassLoader;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        String apkName = "plugin1-debug.apk";
        apkFilePath = Utils.extractAssets(newBase, apkName);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileRelease = getDir("dex", 0); // ... 这个还不是很理解
        dexClassLoader = new DexClassLoader(apkFilePath, fileRelease.getAbsolutePath(),
                null, getClassLoader());

        setBtn1ClickListener("获取插件资源", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResources();
                Class mLoadClassDynamic = null;
                try{
                    mLoadClassDynamic = dexClassLoader.loadClass("lyhao.plugin.plugin1.Dynamic");
                    Object dynamicObject = mLoadClassDynamic.newInstance();

                    IDynamic dynamic = (IDynamic) dynamicObject;
                    String strRes = dynamic.getStringForResId(ResourcePluginActivity.this);
                    btn1.setText(strRes);
                } catch (Exception e){
                    LogUtil.e(TAG, e.toString());
                }
            }
        });
    }

    private void loadResources(){
        try{
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkFilePath);
            mAssetManager = assetManager;
        } catch (Exception e){
            LogUtil.e(TAG, e.toString());
        }

        mResources = new Resources(mAssetManager, super.getResources().getDisplayMetrics(),
                super.getResources().getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }
}
