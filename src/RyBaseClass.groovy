class RyBaseClass extends Base{
    String name;
    RyBaseClass base_class;

    RyBaseClass(String cls_name, fields, RyBaseClass meta_cls, RyBaseClass base_ryclass) {
        super(meta_cls, fields);
        this.name = cls_name;
        this.base_class = base_ryclass;
    }

    def name() {
        return this.name;
    }

    def write_cls(RyBaseClass cls) {
        this.cls = cls;
    }

    def read_base_cls() {
        return this.base_class;
    }

    def method_resolution_order() {
        if (this.base_class == null) {
            return [this];
        } else {
            return [this] + this.base_class.method_resolution_order();
        }
    }

    def issubclass(RyBaseClass cls) {
        return cls.method_resolution_order().contains(this);
    }

    def mixin(Map map) {
        // merge the map
        this._fields << map;
    }

    def _read_from_cls(String mthname) {
        for (RyBaseClass cls : this.method_resolution_order()) {
            if (cls._fields.containsKey(mthname)) {
                return cls._fields[mthname];
            }
        }
        return Global.MISSING;
    }
}