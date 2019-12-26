package lyhao.plugin.study;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lyhao.plugin.study.feature_list.AMSHookActivity2;
import lyhao.plugin.study.feature_list.HostApp;
import lyhao.plugin.study.feature_list.ResourcePluginActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    List<ItemData> datas = new ArrayList<>();
    ItemData itemData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);

        //7.2 资源的插件化解决方案
        itemData = new ItemData();
        itemData.name = "资源的插件化解决方案";
        itemData.actionClass = ResourcePluginActivity.class;
        datas.add(itemData);

        //6.1 加载外部的dex （加载 plugin1-debug.apk 中 dex里的类）
        itemData = new ItemData();
        itemData.name = "加载外部的dex 并获取属性";
        itemData.actionClass = HostApp.class;
        datas.add(itemData);

        //5.4 启动没有在AndroidManifest中声明的Activity
        itemData = new ItemData();
        itemData.name = "启动没有在AndroidManifest中声明的Activity";
        itemData.actionClass = AMSHookActivity2.class;
        datas.add(itemData);

//        //5.2.5 再次对 Instrumentation 字段进行 Hook
//        itemData = new ItemData();
//        itemData.name = "再次对 Instrumentation 字段进行 Hook";
//        itemData.actionClass = InstrumentationHookActivity2.class;
//        datas.add(itemData);
//
//        //5.2.4 对H类的mCallback字段进行Hook
//        itemData = new ItemData();
//        itemData.name = "对H类的mCallback字段进行Hook";
//        itemData.actionClass = Hook_mHActivity.class;
//        datas.add(itemData);
//
//        //5.2.3 对AMN的getDefault方法进行Hook
//        itemData = new ItemData();
//        itemData.name = "对AMN的getDefault方法进行Hook";
//        itemData.actionClass = AMSHookActivity.class;
//        datas.add(itemData);
//
//        //5.2.2 对Activity 的 mInstrumentation 字段进行Hook
//        itemData = new ItemData();
//        itemData.name = "对Activity 的 mInstrumentation 字段进行Hook";
//        itemData.actionClass = InstrumentationHookActivity.class;
//        datas.add(itemData);
//
//        //4.3 对AMN的Hook
//        itemData = new ItemData();
//        itemData.name = "对AMN的Hook";
//        itemData.action = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HookHelper.hookActivityManager();
//                startActivity(new Intent(MainActivity.this, TestActivity.class));
//            }
//        };
//        datas.add(itemData);

        MyAdapter myAdapter = new MyAdapter();
        recycler_view.setAdapter(myAdapter);
        requestPermission();
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycler_view, viewGroup, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            MyHolder myHolder = (MyHolder) viewHolder;
            myHolder.button.setText(datas.get(i).name);
            if(datas.get(i).action != null){
                myHolder.button.setOnClickListener(datas.get(i).action);
            }
            if(datas.get(i).actionClass != null){
                myHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, datas.get(i).actionClass);
                        intent.putExtra("name", datas.get(i).name);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            Button button;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button);
            }
        }
    }

    class ItemData{
        String name;
        Class actionClass;
        View.OnClickListener action;
    }


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void requestPermission() {
        if(Build.VERSION.SDK_INT >Build.VERSION_CODES.LOLLIPOP){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 10001);
            }
        }
    }
}
