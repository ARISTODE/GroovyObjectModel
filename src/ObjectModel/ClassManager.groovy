package ObjectModel

import RuntimeExceptions.ClassExistError

class ClassManager {
    Map<String, RyBaseClass> cls_map;

    public static RyBaseClass _BasicObject = new RyBaseClass("BasicObject", BasicObject.initialize(), null, null);
    public static RyBaseClass _TopObject = new RyBaseClass("TopObject", TopObject.initialize(), null, _BasicObject);
    public static RyBaseClass _RyModule = new RyBaseClass("RyModule", [:], null, _TopObject);
    public static RyBaseClass _RyClass = new RyBaseClass("RyClass", [:], null, _RyModule);
    public static RyBaseClass _RyString = new RyBaseClass("RyString", RyString.initialize(), _RyClass, _TopObject);

    // Numeric operations setup
    public static RyBaseClass _RyNumeric = new RyBaseClass("RyNumeric", RyNumeric.initialize(), _RyClass, _TopObject);
    public static RyBaseClass _RyInteger = new RyBaseClass("RyInteger", RyInteger.initialize(), _RyClass, _RyNumeric);
    public static RyBaseClass _RyFloat = new RyBaseClass("RyFloat", RyFloat.initialze(), _RyClass, _RyNumeric);

    // Boolean classes
    public static RyBaseClass _RyTrueCrlass = new RyBaseClass("TrueClass", TrueClass.initialize(), _RyClass, _TopObject);
    public static RyBaseClass _RyFalseClass = new RyBaseClass("FalseClass", FalseClass.initialize(), _RyClass, _TopObject);

    //other global objects
    public static final Object MISSING = new Object();

    // initialize the comparable methods.
    public static RyBaseClass _RyComparable = new RyBaseClass("RyComparable", RyComparable.initialize(), _RyModule, _TopObject);

    ClassManager() {
        cls_map = new HashMap<String, RyBaseClass>();
        setupClass();
    }

    public void setupClass() {
        cls_map.put("BasicObject", _BasicObject);
        cls_map.put("TopObject", _TopObject);
        cls_map.put("RyModule", _RyModule);
        cls_map.put("RyClass", _RyClass);
        cls_map.put("RyNumeric", _RyNumeric);
        cls_map.put("RyString", _RyString);
        cls_map.put("RyInteger", _RyInteger);
        cls_map.put("RyFloat", _RyFloat);
        cls_map.put("RyTrueClass", _RyTrueCrlass);
        cls_map.put("RyFalseClass",_RyFalseClass );
        cls_map.put("RyComparable", _RyComparable);

        rewriteSuperCls("BasicObject", "RyClass");
        rewriteSuperCls("TopObject", "RyClass");
        rewriteSuperCls("RyModule", "RyClass");
        rewriteSuperCls("RyClass", "RyClass");
        //other global objects
    }

    public void addCls(String cls_name, Map fields, RyBaseClass super_cls, RyBaseClass meta_cls) {
        RyBaseClass res = getCls(cls_name);
        if (res == null) {
            // if not exist yet, produce a class and register it globally
            RyBaseClass tmp = new RyBaseClass(cls_name, fields, meta_cls, super_cls);
            cls_map.put(cls_name, tmp);
        } else {
            throw new ClassExistError("Class Exist!");
        }
    }

    public RyBaseClass removeCls(String cls_name) {
        RyBaseClass tmp = cls_map.get(cls_name);
        cls_map.remove(cls_name);
        return tmp;
    }

    def getCls(String cls_name) {
        return cls_map.get(cls_name);
    }

    def boolean check_cls_exist(String cls_name) {
        return cls_name.contains(cls_name);
    }

    def rewriteSuperCls(String cls_name, String new_super_cls_name) {
        getCls(cls_name).write_cls(getCls(new_super_cls_name));
    }

    def inspectGlobalCls() {
        cls_map.each {k,v -> println("class name: ${k}")}
    }
}
