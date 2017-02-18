class BasicObject {
    def static initialize() {
        def cls_mth_map = [:];
        def __id__ = {Instance self -> return self.toString()};
        def neg = {Instance self -> return !self};
        def not_eql = {Instance self, Instance other -> return self.equals(other)};
        def identity_eql = {Instance self, Instance other -> return self == other};
        def method_add = {Instance self, String name,Closure mth -> self.write_attr(name, mth)};

        cls_mth_map.put("__id__", __id__);
        cls_mth_map.put("neg", neg);
        cls_mth_map.put("not_eql", not_eql);
        cls_mth_map.put("identity_eql", identity_eql);
        cls_mth_map.put("method_add", method_add);

        return cls_mth_map;
    }
}
