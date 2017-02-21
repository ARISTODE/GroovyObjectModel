package ObjectModel
/**
 * Created by hyz on 2017/2/15.
 */
class Global {
    public static RyBaseClass _BasicObject = new RyBaseClass("BasicObject", BasicObject.initialize(), null, null);
    public static RyBaseClass _TopObject = new RyBaseClass("TopObject", TopObject.initialize(), null, _BasicObject);
    public static RyBaseClass _RyModule = new RyBaseClass("RyModule", [:], null, _TopObject);
    public static RyBaseClass _RyClass = new RyBaseClass("RyClass", [:], null, _RyModule);
    public static RyBaseClass _RyString = new RyBaseClass("RyString", RyString.initialize(), _RyClass, _TopObject);

    // Numeric operations setup
    public static RyBaseClass _RyNumeic = new RyBaseClass("RyNumeric", RyNumeric.initialize(), _RyClass, _TopObject);
    public static RyBaseClass _RyInteger = new RyBaseClass("RyInteger", RyInteger.initialize(), _RyClass, _RyNumeic);
    public static RyBaseClass _RyFloat = new RyBaseClass("RyFloat", RyFloat.initialze(), _RyClass, _RyNumeic);

    // Boolean classes
    public static RyBaseClass _RyTrueClass = new RyBaseClass("TrueClass", TrueClass.initialize(), _RyClass, _TopObject);
    public static RyBaseClass _RyFalseClass = new RyBaseClass("FalseClass", FalseClass.initialize(), _RyClass, _TopObject);

    //other global objects
    public static final Object MISSING = new Object();

    // initialize the comparable methods.
    public static RyBaseClass _RyComparable = new RyBaseClass("RyComparable", RyComparable.initialize(), _RyModule, _TopObject);

    public static void class_reference_setup() {
        _BasicObject.write_cls(_RyClass);
        _TopObject.write_cls(_RyClass);
        _RyModule.write_cls(_RyClass);
        _RyClass.write_cls(_RyClass);
    }
}


