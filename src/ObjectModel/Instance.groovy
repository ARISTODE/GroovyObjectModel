package ObjectModel

class Instance extends Base {
    Instance(RyBaseClass cls, default_val="") {
        super(cls, ["__default__":default_val]);
    }
}
