package lyhao.plugin.plugin1;

import lyhao.plugin.mypluginlibrary.IBean;

/**
 * Created by luyanhao on 2019/12/23.
 */
public class Bean implements IBean {
    private String name = "default";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
