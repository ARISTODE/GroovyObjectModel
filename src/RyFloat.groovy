class RyFloat {
    def static initialze() {

        def to_i = {
            self, Object... args ->
                Integer val = ((Float)((Instance)self).default_val()).toInteger();
                return new Instance(Global._RyInteger, val);
        }

        def cls_mth_map = [:];
        cls_mth_map.put("to_i", to_i);
        return cls_mth_map;
    }
}
