package ObjectModel

import RuntimeExceptions.ArgumentTypeError

class RyString {
    def static initialize() {
        def value_standarlize = {
            int a ->
                if (a < 0) {
                    return -1;
                } else if (a > 0) {
                    return 1;
                } else {
                    return 0;
                }
        }

        // overwrite the ceylon method and store the method in the current RyClass fields
        // it get execute firstly in the ancestor chain
        def ceylon = {
            self, Instance... args ->
                String self_val =(self.read_dic("__default__"));
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyString":
                        return new Instance(ClassManager._RyInteger, ["__default__": self_val <=> (String)other.default_val()]);
                    default:
                        throw new ArgumentTypeError("ceylon Method cannot take a ${cls_name} arguments");
                }
        }

        def add = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyString, new_val);
                    case "RyFloat":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyString, new_val);
                    case "RyString":
                        String new_val = self.default_val() + other.default_val();
                        return new Instance(ClassManager._RyString, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def multiply = {
            self, Instance... args ->
                self = (Instance)self;
                Instance other = (Instance)args[0];
                String cls_name = other.read_cls().name();
                String arg_name = other.read_cls().name();

                switch (cls_name) {
                    case "RyInteger":
                        StringBuilder sb = new StringBuilder();
                        int loop_time = (Integer)(other.default_val());
                        int i = 0;

                        while (i < loop_time) {
                            sb.append(self.default_val());
                            i++;
                        }

                        String new_val = sb.toString();
                        return new Instance(ClassManager._RyInteger, new_val);
                    default:
                        throw new ArgumentTypeError("Minus Method cannot take a ${arg_name} arguments");
                        break;
                }
        }

        def length = {
            self, Instance... args ->
                return new Instance(ClassManager._RyInteger, self.read_dic("__default__").length());
        }

        def concat = {
            self, Instance... args ->
                String other = ((Instance)args[0]).read_dic("__default__");
                String new_val = self.read_dic("__default__") + other;
                self.write_attr("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }

        def capitalize = {
            self, Instance... args ->
                return new Instance(ClassManager._RyString, self.read_dic("__default__").capitalize());
        }

        def capitalize_change = {
            self, Instance... args ->
                def new_val = self.read_dic("__default__").capitalize();
                self.write_attr("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }

        def upcase = {
            self, Instance... args ->
                return new Instance(ClassManager._RyString, self.read_dic("__default__").toUpperCase());
        }

        def upcase_change = {
            self, Instance... args ->
                def new_val = self.read_dic("__default__").toUpperCase();
                self.write_attr("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }

        def downcase = {
            self, Instance... args ->
                return new Instance(ClassManager._RyString, self.read_dic("__default__").toLowerCase());
        }

        def downcase_change = {
            self, Instance... args ->
                def new_val = self.read_dic("__default__").toLowerCase();
                self.write_attr("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }


        def getChar = {
            self, Instance... args ->
                return new Instance(ClassManager._RyString, self.read_dic("__default__").charAt((Integer)args[0]));
        }

        def chop = {
            self, Instance... args ->
                return new Instance(ClassManager._RyString, self.read_dic("__default__")[0..-2]);
        }

        def chop_change = {
            self, Instance... args ->
                def new_val = self.read_dic("__default__")[0..-2];
                self.write_dic("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }

        def empty = {
            self, Instance... args ->
                return self.read_dic("__default__") == "" ? new Instance(ClassManager._RyTrueClass, true) : new Instance(ClassManager._RyFalseClass, false);
        }

        def insert = {
            self, Instance... args ->
                // (Integer, StringToInsert)
                Integer index = (Integer)args[0];
                String stub = (String)args[1];
                String val = self.default_val();
                String new_val = val.substring(0, index) + stub + val.substring(index, val.length());
                self.write_dic("__default__", new_val);
                return new Instance(ClassManager._RyString, new_val);
        }

        def cls_mth_map = [:];
        cls_mth_map.put("ceylon", ceylon); // <=>
        cls_mth_map.put("add", add);
        cls_mth_map.put("multiply", multiply);
        cls_mth_map.put("length", length);
        cls_mth_map.put("concat", concat);
        cls_mth_map.put("capitalize", capitalize);
        cls_mth_map.put("capitalize_change", capitalize_change);
        cls_mth_map.put("upcase", upcase);
        cls_mth_map.put("upcase_change", upcase_change);
        cls_mth_map.put("chop", chop);
        cls_mth_map.put("chop_change", chop_change);
        cls_mth_map.put("getChar", getChar);
        cls_mth_map.put("downcase", downcase);
        cls_mth_map.put("downcase_change", downcase_change);
        cls_mth_map.put("empty", empty);
        cls_mth_map.put("insert", insert);

        return cls_mth_map;
    }
}
