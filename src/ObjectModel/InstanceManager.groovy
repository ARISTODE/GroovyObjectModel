package ObjectModel

class InstanceManager {
    private Map<String, Instance> instance_map;
    private ClassManager cls_manager;
    private String self;

    InstanceManager(ClassManager cls_manager) {
        instance_map = new HashMap<String, Instance>();
        this.cls_manager = cls_manager;
        addInstance("main", new Instance(cls_manager.getCls("TopObject")));
        // setting up main as the initial context object at the beginning of each program
        self = "main";
    }

    public void addInstance(String ins_name, Instance ins) {
        instance_map.put(ins_name, ins);
    }

    public Instance removeInstance(String ins_name) {
        Instance tmp = instance_map.get(ins_name);
        instance_map.remove(ins_name);
        return tmp;
    }

    public Instance getInstance(String ins_name) {
        return instance_map.get(ins_name);
    }

    public String current_obj_name() {
        return self;
    }

    public Instance current_obj() {
        return getInstance(self);
    }

    public void setCurrentObj(String name) {
        this.self = name;
    }
}