package ObjectModel

import RuntimeExceptions.ArgumentTypeError

class RyInteger {
    def static initialize() {
        def ceylon = {
            Instance self, Instance... args ->
                Integer self_val = self.read_attr("__default__");
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        return new Instance(ClassManager._RyInteger, ["__default__": self_val <=> (Integer)other.default_val()]);
                    case "RyFloat":
                        return new Instance(ClassManager._RyInteger, ["__default__": self_val <=> (Float)other.default_val()]);
                    default:
                        throw new ArgumentTypeError("ceylon Method cannot take a ${cls_name} arguments");
                }
        }

        def add = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyInteger, ["__default__": new_val]);
                    case "RyFloat":
                        float new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyFloat, ["__default__": new_val]);
                    case "RyString":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyString, ["__default__": new_val]);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def minus = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() - other.default_val();
                        return new Instance(ClassManager._RyInteger, ["__default__": new_val]);
                    case "RyFloat":
                        float new_val = self.default_val() - other.default_val();
                        return new Instance(ClassManager._RyFloat, ["__default__": new_val]);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def multiply = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() * other.default_val();
                        return new Instance(ClassManager._RyInteger, ["__default__": new_val]);
                    case "RyFloat":
                        float new_val = self.default_val() * other.default_val();
                        return new Instance(ClassManager._RyFloat, ["__default__": new_val]);
                    default:
                        throw new ArgumentTypeError("Multiply Method cannot take a ${cls_name} arguments");
                        break;
                }
        }


        def div = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() / other.default_val();
                        return new Instance(ClassManager._RyInteger, ["__default__": new_val]);
                    case "RyFloat":
                        float new_val = self.default_val() / other.default_val();
                        return new Instance(ClassManager._RyFloat, ["__default__": new_val]);
                    default:
                        throw new ArgumentTypeError("Multiply Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def module = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() % other.default_val();
                        return new Instance(ClassManager._RyFloat, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() % other.default_val();
                        return new Instance(ClassManager._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Module Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def exponent = {
            self, Instance... args ->
                return new Instance(ClassManager._RyInteger, Math.pow(self.default_val(), (Double)args[0]));
        }

        def shift_left = {
            self, Instance... args ->
                Integer bits = (Integer)args[0];
                return new Instance(ClassManager._RyInteger, ((Instance)self).default_val() << bits);
        }

        def shift_right = {
            self, Instance... args ->
                Integer bits = (Integer)args[0];
                return new Instance(ClassManager._RyInteger, ((Instance)self).default_val() >> bits);
        }

        def is_even = {
            self, Instance... args ->
                return ((Instance)self).default_val() % 2 == 0 ? new Instance(ClassManager._RyTrueClass, true) : new Instance(ClassManager._RyFalseClass, false);
        }

        def is_odd = {
            self, Instance... args ->
                return ((Instance)self).default_val() % 2 != 0 ? new Instance(ClassManager._RyTrueClass, true) : new Instance(ClassManager._RyFalseClass, false);
        }

        def to_s = {
            self, Instance... args ->
                String val = ((Instance)self).default_val().toString();
                // return a new String Instance with the new value encapsulated
                return new Instance(ClassManager._RyString, val);
        }

        def to_f = {
            self, Instance... args ->
                float val = ((Integer)((Instance)self).default_val()).toFloat();
                return new Instance(ClassManager._RyFloat, val);
        }

        def cls_mth_map = [:];
        cls_mth_map.put("ceylon", ceylon);
        cls_mth_map.put("add", add);
        cls_mth_map.put("minus", minus);
        cls_mth_map.put("multiply", multiply);
        cls_mth_map.put("div", div);
        cls_mth_map.put("exponent", exponent);
        cls_mth_map.put("shift_left", shift_left);
        cls_mth_map.put("shift_right", shift_right);
        cls_mth_map.put("is_even", is_even);
        cls_mth_map.put("is_odd", is_odd);
        cls_mth_map.put("to_s", to_s);
        cls_mth_map.put("to_f", to_f);

        return cls_mth_map;
    }
}
