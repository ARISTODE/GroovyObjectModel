
import ObjectModel.*;

class testClass {
    public static void main(String[] argvs) {
        ClassManager class_manager = new ClassManager();
        InstanceManager instance_manager = new InstanceManager(class_manager);
        




class_manager.addCls("A", ["initialize": {self, Object... args -> }], class_manager.getCls("RyClass"), class_manager.getCls("TopObject"))


class_manager.getCls("A").write_attr("initialize", { 
 self, Object... args -> 
 Instance a = (Instance)args[0];
 
 	
self.write_attr("@a", a);

 })
class_manager.getCls("A").write_attr("sayHello", { 
self, Object... args -> 
 	
return self.read_attr("@a");
 })
instance_manager.addInstance("c", new Instance(class_manager.getCls("RyInteger"), ["__default__":5]))
instance_manager.addInstance("a", new Instance(class_manager.getCls("A")));

instance_manager.getInstance("a").callmethod("initialize", new Instance(class_manager.getCls("RyInteger"), ["__default__":5]))
println(((Instance)instance_manager.getInstance("a").callmethod("sayHello")).default_val())
    }
    
}
