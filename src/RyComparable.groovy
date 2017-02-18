class RyComparable {
     def static initialize() {
        def less_than = {
            self, Object... args ->
            return self.callmethod("ceylon",args) < 0;
        };

        def less_eql_than = {
            self, Object... args ->
                def res = self.callmethod("ceylon", args);
                return res == 0 || res < 0;
        };

        def bigger_eql_than = {
            self, Object... args ->
                def res = self.callmethod("ceylon", args);
                return res == 0 || res > 0;
        }

        def bigger_than = {
            self, Object... args ->
            return self.callmethod("ceylon", args) > 0;
        };

        def is_equal = {
            self, Object... args ->
            return self.callmethod("ceylon", args) == 0;
        }

        def cls_mth_map = [:];
        cls_mth_map.put("less_than", less_than);
        cls_mth_map.put("bigger_than", bigger_than);
        cls_mth_map.put("less_eql_than", less_eql_than);
        cls_mth_map.put("bigger_eql_than", bigger_eql_than);
        cls_mth_map.put("is_equal", is_equal);

        return cls_mth_map;
    }
}
