package ObjectModel

class ScopeManager {
    public static Map<String, ArrayList> global_scope_map = new HashMap<>();

    public static void addScope(String scope_name) {
        global_scope_map.put(scope_name, []);
    }

    public static void addInstanceToScope(String scope_name, String val_name) {
        global_scope_map.get(scope_name) == null ? global_scope_map.put(scope_name, [val_name]) : global_scope_map.get(scope_name).add(val_name);
    }

    public static boolean checkInstanceExistence(String scope_name, String val_name) {
        def res = global_scope_map.get(scope_name) == null ? false : global_scope_map.get(scope_name).contains(val_name);
    }
}
