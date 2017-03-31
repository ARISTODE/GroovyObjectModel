package ObjectModel

class TmpScriptTest {
    public static void main(String[] argvs) {
        ClassManager class_manager = new ClassManager();
        Instance main = new Instance(class_manager.getCls("TopObject"));
        Instance current_obj = main;
        class_manager.addCls("RyMath", ["initialize": {self, Object... args -> }], class_manager.getCls("RyClass"), class_manager.getCls("TopObject"))
        class_manager.getCls("RyMath").write_attr("fib", {
            Instance self, Object... args ->
                current_obj = self;
                Instance n = (Instance)args[0];

                if ((n.callmethod("ceylon", new Instance(class_manager.getCls("RyInteger"), ["__default__":2])).default_val()  == 0)||(n.callmethod("ceylon", new Instance(class_manager.getCls("RyInteger"), ["__default__":1])).default_val()  == 0)) {
                    return n
                } else {
                    return current_obj.callmethod("fib",n.callmethod("minus",new Instance(class_manager.getCls("RyInteger"), ["__default__":1]))).callmethod("add",current_obj.callmethod("fib",n.callmethod("minus",new Instance(class_manager.getCls("RyInteger"), ["__default__":2]))))
                }
        })

        class_manager.getCls("RyMath").write_attr("addTwoNum", {
            Instance self, Object... args ->
                current_obj = self;
                Instance a = (Instance)args[0];
                Instance b = (Instance)args[1];

                return a.callmethod("add",b)
        })

        Instance math_obj = new Instance(class_manager.getCls("RyMath"));

        current_obj.callmethod("puts",math_obj.callmethod("fib", new Instance(class_manager.getCls("RyInteger"), ["__default__":5])))
        current_obj.callmethod("puts",math_obj.callmethod("addTwoNum", new Instance(class_manager.getCls("RyInteger"), ["__default__":3]),new Instance(class_manager.getCls("RyInteger"), ["__default__":4])))
    }

}
