package ObjectModel

class InstanceManager {
    public static Map<String, ArrayList> instance_scope_map = new HashMap<>();
    // store the scope withe string name of the scope and the list of variables it has
    private ClassManager cls_manager;
    private String self;

    InstanceManager(ClassManager cls_manager) {
        instance_scope_map = new HashMap();
        this.cls_manager = cls_manager;
//        addInstance("main", new Instance(cls_manager.getCls("TopObject")));
        instance_scope_map.put("main", []);
        // setting up main as the initial context object at the beginning of each program
//        self = "main";
    }

    public void addScope(String scope_name) {
        instance_scope_map.put(scope_name, []);
    }

    public void addInstanceToScope(String scope_name, Instance val) {
        instance_scope_map.get(scope_name).add(val);
    }

    public boolean checkInstanceExistance(String scope_name, Instance val) {
        return instance_scope_map.get(scope_name).contains(val);
    }

//    public void addInstance(String ins_name, Instance ins) {
//        instance_map.put(ins_name, ins);
//    }
//
//    public Instance removeInstance(String ins_name) {
//        Instance tmp = instance_map.get(ins_name);
//        instance_map.remove(ins_name);
//        return tmp;
//    }
//
//    public Instance getInstance(String ins_name) {
//        return instance_map.get(ins_name);
//    }
//
//    public String current_obj_name() {
//        return self;
//    }
//
//    public Instance current_obj() {
//        return getInstance(self);
//    }
//
//    public void setCurrentObj(String name) {
//        this.self = name;
//    }
}