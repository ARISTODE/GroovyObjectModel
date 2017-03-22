package ObjectModel

import RuntimeExceptions.ArgumentTypeError

class FalseClass {
    def static initialize() {
        def and = {
            self, Object... args ->
                self = Instance(self);
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "ObjectModel.TrueClass":
                        return new Instance(ClassManager._RyFalseClass, false);
                    case "ObjectModel.FalseClass":
                        return new Instance(ClassManager._RyFalseClass, false);
                    default:
                        throw new ArgumentTypeError("and Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def or = {
            self, Object... args ->
                self = Instance(self);
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "ObjectModel.TrueClass":
                        return new Instance(ClassManager._RyTrueClass, true);
                    case "ObjectModel.FalseClass":
                        return new Instance(ClassManager._RyFalseClass, false);
                    default:
                        throw new ArgumentTypeError("and Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def to_s = {
            self, Object... args ->
                return new Instance(ClassManager._RyString, "false");
        }

        def cls_mth_map = [:];
        cls_mth_map.put("and", and);
        cls_mth_map.put("or", or);
        cls_mth_map.put("to_s", to_s);
        return cls_mth_map;
    }
}
