package ObjectModel

class RyNumeric {
    def static initialize() {
        def ceylon = {
            self, Instance... args ->
                def self_val = self.default_val();
                def other_val = ((Instance)args[0]).default_val();
                return self_val <=> other_val;
        };

        def ceil = {
            self, Instance... args ->
                return new Instance(ClassManager._RyInteger, Math.ceil(self.default_val()));
        };

        def floor = {
            self, Instance... args ->
                return new Instance(ClassManager._RyInteger, Math.floor(self.default_val()));
        };

        def abs = {
            self, Instance... args ->
                return Math.abs(self.default_val());
        }

        def add = {
            self, Instance... args ->
                return self.default_val() + ((Instance)args[0]).default_val();
        }

        def minus = {
            self, Instance... args ->
                return self.default_val() - ((Instance)args[0]).default_val();
        }

        def div = {
            self, Instance... args ->
                return self.default_val() / ((Instance)args[0]).default_val();
        }

        def module = {
            self, Instance... args ->
                return self.deault_val() % ((Instance)args[0]).default_val();
        }

        def cls_mth_map = [:];
        cls_mth_map.put("ceylon", ceylon);
        cls_mth_map.put("ceil", ceil);
        cls_mth_map.put("floor", floor);
        cls_mth_map.put("abs", abs);
        cls_mth_map.put("add", add);
        cls_mth_map.put("minus", minus);
        cls_mth_map.put("div", div);
        cls_mth_map.put("module", module);

        return cls_mth_map;
    }
}
