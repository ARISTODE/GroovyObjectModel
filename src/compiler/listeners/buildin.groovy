package compiler.listeners

class Buildin {
    // some special methods that need to be treated differently
    public static hooking_methods = ["initialize"];
    public static basic_values = ["RyInteger", "RyFloat", "RyString"];
    public static buildin_methods = ["puts", "ceylon", "to_i", "to_s"];
}
