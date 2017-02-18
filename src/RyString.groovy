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
            self, Object... args ->
                String self_str =(self.read_dic("__default__"));
                String other_str = ((Instance)args[0]).read_dic( "__default__");
                return value_standarlize(self_str <=> other_str);
        }

        def length = {
            self, Object... args ->
                return self.read_dic("__default__").length();
        }

        def concat = {
            self, Object... args ->
                String other = ((Instance)args[0]).read_dic("__default__");
                String new_val = self.read_dic("__default__") + other;
                self.write_attr("__default__", new_val);
                return new_val;
        }

        def capitalize = {
            self, Object... args ->
                return self.read_dic("__default__").capitalize();
        }

        def capitalize_change = {
            self, Object... args ->
                def new_val = self.read_dic("__default__").capitalize();
                self.write_attr("__default__", new_val);
                return new_val;
        }

        def upcase = {
            self, Object... args ->
                return self.read_dic("__default__").toUpperCase();
        }

        def upcase_change = {
            self, Object... args ->
                def new_val = self.read_dic("__default__").toUpperCase();
                self.write_attr("__default__", new_val);
                return new_val;
        }

        def downcase = {
            self, Object... args ->
                return self.read_dic("__default__").toLowerCase();
        }

        def downcase_change = {
            self, Object... args ->
                def new_val = self.read_dic("__default__").toLowerCase();
                self.write_attr("__default__", new_val);
                return new_val;
        }


        def getChar = {
            self, Object... args ->
                return self.read_dic("__default__").charAt((Integer)args[0]);
        }

        def chop = {
            self, Object... args ->
                return self.read_dic("__default__")[0..-2];
        }

        def chop_change = {
            self, Object... args ->
                def new_val = self.read_dic("__default__")[0..-2];
                self.write_dic("__default__", new_val);
                return new_val;
        }

        def empty = {
            self, Object... args ->
                return self.read_dic("__default__") == "";
        }

        def insert = {
            self, Object... args ->
                // (Integer, StringToInsert)
                Integer index = (Integer)args[0];
                String stub = (String)args[1];
                String val = self.default_val();
                String new_val = val.substring(0, index) + stub + val.substring(index, val.length());
                self.write_dic("__default__", new_val);
                return new_val;
        }

        def cls_mth_map = [:];
        cls_mth_map.put("ceylon", ceylon); // <=>
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
