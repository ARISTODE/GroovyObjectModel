package ObjectModel

class TopObject {
    def static initialize() {
        def cls_mth_map = [:];

        def __id__ = {
            self, Instance... args ->
            return self.toString();
        };

        def ceylon = {
            self, Instance... args ->
            if (self == args[0]) {
                return args[0];
            } else {
                return null;
            }
        };

        def case_eql = {
            self, Instance... args ->
                return self == args[0];
        };  // === always overwritten by the children class to implement a different equality checking

        def class_name = {
            self, Instance... args ->
                return self.cls.name();
        };

        def is_a = {
           self, Instance... args ->
                return self.cls.method_resolution_order().contains((RyBaseClass)args[0]);
        };

        def to_s = {
            self, Instance... args ->
                // first fetch the default value of the class. If no, return the identity of the object
                if (self.read_dic("__default__") != null) {
                    return self.read_dic("__default__");
                }
                return self.toString()
        };

        cls_mth_map.put("object_id", __id__);
        cls_mth_map.put("ceylon", ceylon);
        cls_mth_map.put("case_eql", case_eql);
        cls_mth_map.put("class_name", class_name);
        cls_mth_map.put("is_a", is_a);
        cls_mth_map.put("to_s", to_s);

        return cls_mth_map;
    }
}
