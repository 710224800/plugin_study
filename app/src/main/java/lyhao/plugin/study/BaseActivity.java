package lyhao.plugin.study;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by luyanhao on 2019/12/3.
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "Plugin_Test";

    protected TextView jumpToTest;
    protected TextView title;
    protected Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        title = findViewById(R.id.title);
        jumpToTest = findViewById(R.id.jumpToTest);
        btn1 = findViewById(R.id.btn1);
        try {
            String name = getIntent().getExtras().getString("name", "null");
            title.setText(name);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }

        jumpToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, TestActivity.class));
            }
        });
        jumpToTest.setVisibility(View.VISIBLE);
    }

    protected void setBtn1ClickListener(String desc, View.OnClickListener listener){
        btn1.setText(desc);
        btn1.setOnClickListener(listener);
        btn1.setVisibility(View.VISIBLE);
    }

    protected AssetManager mAssetManager;
    protected Resources mResources;
    protected Resources.Theme mTheme;

    @Override
    public AssetManager getAssets() {
        if(mAssetManager == null) {
            return super.getAssets();
        }
        return mAssetManager;
    }

    @Override
    public Resources getResources() {
        if(mResources == null) {
            return super.getResources();
        }
        return mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        if(mTheme == null) {
            return super.getTheme();
        }
        return mTheme;
    }
}
