package compiler;
import ObjectModel.*;

public class test {
    public static void main(String[] args) {
        // a = 3
        Instance a = new Instance(Global._RyInteger, 3);
        // b = 4.5
        Instance b = new Instance(Global._RyFloat, 3.5);
//        System.out.println(((Instance)a.callmethod("add", b)).default_val());
        Instance c = (Instance)a.callmethod("add",b);
        System.out.println(c.default_val());
    }
}
