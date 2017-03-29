package ObjectModel

class TmpScriptTest {
    public static void main(String[] argvs) {
        ClassManager class_manager = new ClassManager();


        class_manager.addCls("A", ["initialize": {self, Object... args -> }], class_manager.getCls("RyClass"), class_manager.getCls("TopObject"))


        class_manager.getCls("A").write_attr("initialize", {
            self, Object... args ->
                self = (Instance)self;
                Instance a = (Instance)args[0];


                self.write_attr("@a", a);

        })
        class_manager.getCls("A").write_attr("sayHello", {
            self, Object... args ->
                self = (Instance)self;

                return self.read_attr("@a")
        })
        Instance c = new Instance(class_manager.getCls("RyInteger"), ["__default__":5])
        Instance a = new Instance(class_manager.getCls("A"));

        a.callmethod("initialize", new Instance(class_manager.getCls("RyInteger"), ["__default__":5]))
        println(((Instance)a.callmethod("sayHello")).default_val())
    }

}
