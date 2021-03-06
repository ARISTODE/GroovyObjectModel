class Base {
    RyBaseClass cls;
    def _fields = [:];

    Base(RyBaseClass cls, fields) {
        this.cls = cls;
        this._fields = fields;
    }

    def read_attr(String fieldname) {
        def res = this.read_dic(fieldname);
        if (res != null) {
            return res;
        }

        res = this.cls._read_from_cls(fieldname);
        if (is_method(res)) {
            return _make_bound_method(this, res);
        }

        if (res != null) {
            return res;
        }
        return null;
    }

    def write_attr(String fieldname, value) {
        write_dic(fieldname, value);
    }

    def read_dic(String fieldname) {
        return this._fields.get(fieldname);
    }

    def write_dic(String filedname, value) {
        this._fields.put(filedname, value);
    }

    def read_cls() {
        return this.cls;
    }

    def isinstance(RyBaseClass cls) {
        return this.cls.issubclass(cls)
    }

    def callmethod(String mtdname, Object... args) {
        // mth is Closure
        def mth = read_attr(mtdname);
        // return the closure with self and args bind to it
        return mth(args);
    }

    def is_method(res) {
        return res instanceof Closure;
    }

    // return a method with self and args bindings
    def _make_bound_method(self, mth) {
        def bound = {Object... args -> return mth(self, args)};
        return bound;
    }

    def default_val() {
        def field = read_dic("__default__");
        if (field == null) {
            return "";
        }
        return field;
    }
}
