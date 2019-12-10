package lyhao.plugin.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lyhao.plugin.study.feature_list.AMSHookActivity;
import lyhao.plugin.study.feature_list.Hook_mHActivity;
import lyhao.plugin.study.feature_list.InstrumentationHookActivity;
import lyhao.plugin.study.feature_list.InstrumentationHookActivity2;
import lyhao.plugin.study.hooktest.HookHelper;

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

        itemData = new ItemData();
        itemData.name = "hookInstrumentation2";
        itemData.actionClass = InstrumentationHookActivity2.class;
        datas.add(itemData);

        itemData = new ItemData();
        itemData.name = "Hook_mHActivity";
        itemData.actionClass = Hook_mHActivity.class;
        datas.add(itemData);

        itemData = new ItemData();
        itemData.name = "AMSHookActivity";
        itemData.actionClass = AMSHookActivity.class;
        datas.add(itemData);

        itemData = new ItemData();
        itemData.name = "hookInstrumentation";
        itemData.actionClass = InstrumentationHookActivity.class;
        datas.add(itemData);

        //这是一个功能节点示例--start--
        itemData = new ItemData();
        itemData.name = "hookActivityManager";
        itemData.action = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookHelper.hookActivityManager();
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        };
        datas.add(itemData);
        //功能节点示例--end--

        MyAdapter myAdapter = new MyAdapter();
        recycler_view.setAdapter(myAdapter);
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
}
