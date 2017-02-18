class RyInteger {
    def static initialize() {

        def multiply = {
            self, Object... args ->
                return self.default_val() * ((Instance)args[0]).default_val();
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
        cls_mth_map.put("multiply", multiply);
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
