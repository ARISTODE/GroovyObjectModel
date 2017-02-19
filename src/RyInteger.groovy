import RuntimeExceptions.ArgumentTypeError

class RyInteger {
    def static initialize() {

        def add = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyInteger, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyString":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyString, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def minus = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() - other.default_val();
                        return new Instance(Global._RyInteger, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() - other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def multiply = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() * other.default_val();
                        return new Instance(Global._RyInteger, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() * other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Multiply Method cannot take a ${cls_name} arguments");
                        break;
                }
        }


        def div = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        int new_val = self.default_val() / other.default_val();
                        return new Instance(Global._RyInteger, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() / other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Multiply Method cannot take a ${cls_name} arguments");
                        break;
                }
        }

        def exponent = {
            self, Object... args ->
                return Math.pow(self.default_val(), (Double)args[0]);
        }

        def shift_left = {
            self, Object... args ->
                Integer bits = (Integer)args[0];
                return ((Instance)self).default_val() << bits;
        }

        def shift_right = {
            self, Object... args ->
                Integer bits = (Integer)args[0];
                return ((Instance)self).default_val() >> bits;
        }

        def is_even = {
            self, Object... args ->
                return ((Instance)self).default_val() % 2 == 0;
        }

        def is_odd = {
            self, Object... args ->
                return !is_even(self, args);
        }

        def to_s = {
            self, Object... args ->
                String val = ((Instance)self).default_val().toString();
                // return a new String object with the new value encapsulated
                return new Instance(Global._RyString, val);
        }

        def to_f = {
            self, Object... args ->
                float val = ((Integer)((Instance)self).default_val()).toFloat();
                return new Instance(Global._RyFloat, val);
        }

        def cls_mth_map = [:];
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
