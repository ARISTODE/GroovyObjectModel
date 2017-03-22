package ObjectModel

class Test {
    public static void main(String[] argvs) {
        ClassManager cls_manager = new ClassManager();
        InstanceManager instance_manager = new InstanceManager(cls_manager);
        // a = 5
        instance_manager.addInstance("a", new Instance(cls_manager.getCls("RyInteger"), ["__default__": 5]));
        // b = 4
        instance_manager.addInstance("b", new Instance(cls_manager.getCls("RyInteger"), ["__default__": 4]));

        // c = a + b
        instance_manager.addInstance("c", (Instance)instance_manager.getInstance("a").callmethod("add", instance_manager.getInstance("b")));

        // puts c
        println(instance_manager.getInstance("c").default_val());

        // c = 4.5
        instance_manager.addInstance("c", new Instance(cls_manager.getCls("RyFloat"), ["__default__": 4.5]));

        // function definition (groovy)
        // here use the idea of singleton methods. Simple write the method to the field of the object instead of its' class map
        instance_manager.current_obj().write_attr("add_num", {
            self, Object... args ->
                Instance a = (Instance)args[0];
                Instance b = (Instance)args[1];
                return a.callmethod("add", b);
        });

        // function call
        println(instance_manager.getInstance("main").callmethod("add_num", instance_manager.getInstance("a"), instance_manager.getInstance("c")));

        // condition control (if-else)
        if (instance_manager.getInstance("a").callmethod("ceylon", instance_manager.getInstance("b")) > 0) {
            println("great")
        } else if (instance_manager.getInstance("a").callmethod("ceylon", instance_manager.getInstance("b")) < 0) {
            println("less")
        } else {
            println("they are equal")
        }

        // define a new class
        /*
        *  class A < RyInteger
        *       def initialize(a,b)
        *           @a = a
        *           @b = b
        *       end
        *
        *       def get_a()
        *           return @a
        *       end
        *
        *       def get_b()
        *           return @b
        *       end
        *  end
        *
        *  A.new(5,4.5)
        * */

        // stuff the default initialize method (groovy)
        cls_manager.addCls("A", ["initialize": {self, Object... args -> }], cls_manager.getCls("RyClass"),cls_manager.getCls("RyInteger"));
        instance_manager.addInstance("first_a", new Instance(cls_manager.getCls("A")));

        // write initialize method
        cls_manager.getCls("A").write_attr("initialize", {
            Instance self, Object... args ->
                // the self here refer to the object instead of its' class
                Instance a = (Instance)args[0];
                Instance b = (Instance)args[1];
                self.write_attr("a", a);
                self.write_attr("b", b);
        });

        // call initialize method immediately after the creation of the object
        instance_manager.getInstance("first_a").callmethod("initialize", new Instance(cls_manager.getCls("RyInteger"), ["__default__": 5]), new Instance(cls_manager.getCls("RyFloat"), ["__default__": 4.5]));

        // write get_a / get_b method
        cls_manager.getCls("A").write_attr("get_a", {
            self, Object... args ->
                println(((Instance)self.read_attr("a")).default_val());
        });

        cls_manager.getCls("A").write_attr("get_b", {
            self, Object... args ->
                println(((Instance)self.read_attr("b")).default_val());
        });

        // first_a.sayHello()
        instance_manager.getInstance("first_a").callmethod("get_a");
        instance_manager.getInstance("first_a").callmethod("get_b")
    }
}
