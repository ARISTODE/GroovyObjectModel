package ObjectModel

class RyIO {
    def static initialize() {
        def puts = {
            Instance self, Instance... args ->
                for(Instance arg : args) {
                    if (arg._fields.containsKey("__default__")) {
                        println(arg._fields["__default__"]);
                    } else {
                        println(arg.toString());
                    }
                }
        };

        def print = {
            Instance self, Instance... args ->
                for(Instance arg : args) {
                    if (arg._fields.containsKey("__default__")) {
                        print(arg._fields["__default__"]);
                    } else {
                        print(arg.toString());
                    }
                }
        };

        def cls_mth_map = [:];
        cls_mth_map.put("puts", puts);
        cls_mth_map.put("print", print);
        return cls_mth_map;
    }
}
