package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;

import dalvik.system.DexClassLoader;
import lyhao.plugin.mypluginlibrary.IBean;
import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.util.LogUtil;
import lyhao.plugin.study.util.Utils;

/**
 * Created by luyanhao on 2019/12/17.
 */
public class HostApp extends BaseActivity {
    public static final String TAG = HostApp.class.getSimpleName();
    private String filePath;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        filePath = Utils.extractAssets(newBase, "plugin1-debug.apk");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBtn1ClickListener("获取插件dex 类中的属性", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileRelease = getDir("dex", 0);
                ClassLoader classLoader = new DexClassLoader(filePath, fileRelease.getAbsolutePath(), null, getClassLoader());

                try {
                    Class mLoadClassBean = classLoader.loadClass("lyhao.plugin.plugin1.Bean");
                    Object beanObject = mLoadClassBean.newInstance();

                    // 面向接口之前需要这样做：
//                    Method getNameMethod = mLoadClassBean.getMethod("getName");
//                    getNameMethod.setAccessible(true);
//                    String name = (String) getNameMethod.invoke(beanObject);

                    //6.2 面向接口编程后就可以这样了：
                    IBean bean = (IBean) beanObject;
                    String name = bean.getName();
                    btn1.setText(name);
                    LogUtil.d(TAG, "设置前name = " + name);
                    bean.setName("小明");
                    name = bean.getName();
                    LogUtil.d(TAG, "设置后name = " + name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
