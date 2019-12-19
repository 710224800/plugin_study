package lyhao.plugin.study;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * Created by luyanhao on 2019/12/3.
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "Plugin_Test";

    protected TextView jumpToTest;
    protected TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        title = findViewById(R.id.title);
        jumpToTest = findViewById(R.id.jumpToTest);
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
    }
}
