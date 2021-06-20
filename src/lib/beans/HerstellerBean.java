package lib.beans;

import java.io.*;

public class HerstellerBean implements Serializable, Bean {

    private String name;

    public HerstellerBean() {
    }

    public HerstellerBean(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HerstellerBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
