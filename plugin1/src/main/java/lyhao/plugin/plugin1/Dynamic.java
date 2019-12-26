package lyhao.plugin.plugin1;

import android.content.Context;

import lyhao.plugin.mypluginlibrary.IDynamic;

/**
 * Created by luyanhao on 2019/12/25.
 */
public class Dynamic implements IDynamic {
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.hello_world);
    }
}
