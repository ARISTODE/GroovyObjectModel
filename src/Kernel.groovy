/**
 * Created by hyz on 2017/2/16.
 */
class Kernel {
    def static initialize() {
        def puts = {
            Instance self ->
            if (self._fields.containsKey("__default")) {
                println(self._fields["__default"]);
            } else {
                println(self);
            }
        };

        def cls_mth_map = [:];
        cls_mth_map.put("puts", puts);
        return cls_mth_map;
    }
}
