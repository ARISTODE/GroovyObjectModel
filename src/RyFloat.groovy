import RuntimeExceptions.ArgumentTypeError

class RyFloat {
    def static initialze() {

        def add = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        float new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyString":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(Global._RyString, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def minus = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        float new_val = self.default_val() - other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() - other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def multiply = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        float new_val = self.default_val() * other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() * other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }


        def div = {
            self, Object... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        float new_val = self.default_val() / other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    case "RyFloat":
                        float new_val = self.default_val() / other.default_val();
                        return new Instance(Global._RyFloat, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def to_i = {
            self, Object... args ->
                Integer val = ((Float)((Instance)self).default_val()).toInteger();
                return new Instance(Global._RyInteger, val);
        }

        def cls_mth_map = [:];
        cls_mth_map.put("add", add);
        cls_mth_map.put("minus", minus);
        cls_mth_map.put("multiply", multiply);
        cls_mth_map.put("div", div);
        cls_mth_map.put("to_i", to_i);
        return cls_mth_map;
    }
}
