package pojo;

/**
 * Created by Jinffee on 2017/9/5.
 */

public class Doctor {
    int code;
    String dep;
    String name;
    Byte[] icon;
    public Doctor(int code, String dep, String name, Byte[] icon){
        this.code = code;
        this.dep = dep;
        this.name = name;
        this.icon = icon;
    }
    public int getcode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte[] getIcon() {
        return icon;
    }

    public void setIcon(Byte[] icon) {
        this.icon = icon;
    }
}
