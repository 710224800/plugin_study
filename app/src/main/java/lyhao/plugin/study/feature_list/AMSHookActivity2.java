package lyhao.plugin.study.feature_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import lyhao.plugin.study.BaseActivity;
import lyhao.plugin.study.NoRegisterActivity;
import lyhao.plugin.study.TestActivity;
import lyhao.plugin.study.hooktest.AMSHookHelper;

/**
 * Created by luyanhao on 2019/12/13.
 */
public class AMSHookActivity2 extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        AMSHookHelper amsHookHelper = new AMSHookHelper();
        amsHookHelper.hookAMN();
        amsHookHelper.hookInstrumentation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jumpToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AMSHookActivity2.this, NoRegisterActivity.class));
            }
        });
    }
}
