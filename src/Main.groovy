class Main {
    public static void main(String[] args) {
        Global.class_reference_setup();
        //TODO: current using map directly, later will use a single groovy class to describe the Module
        Global._TopObject.mixin(Kernel.initialize());
        Global._TopObject.mixin(RyComparable.initialize());

        Instance _main = new Instance(Global._TopObject);
        Instance str1 = new Instance(Global._RyString, "Hello");
        Instance str2 = new Instance(Global._RyString, "hello");
        Instance str3 = new Instance(Global._RyString, "");
        // self is passed to the method automatically as this(Base).

        // Testing
        // Top Object checking
        assert str1.callmethod("ceylon", str2) == -1;
        assert str1.callmethod("class_name") == "RyString" : "class name method fail (TopObject)";
        assert str1.callmethod("to_s") == "Hello";
        assert str1.callmethod("is_a", Global._TopObject) == true;
        println(str1.callmethod("object_id", Global._TopObject));

        // Comparable
        assert str1.callmethod("less_than",str2) ==  true: "less_than method fail (Comparable)";
        assert str1.callmethod("bigger_than", str2) == false : "bigger_than method fail (Comparable)";
        assert str1.callmethod("bigger_eql_than", new Instance(Global._RyString, "Hello")) == true : "bigger_eql_than method fail (Comparable)";
        assert str1.callmethod("less_eql_than", new Instance(Global._RyString, "Hello")) == true : "less_eql_than method fail (Comparable)";
        assert str1.callmethod("is_equal", new Instance(Global._RyString, "Hello")) == true : "is_equal method fail (Comparable)";

        // String methods checking
        assert str1.callmethod("length") == 5 : "length test fail";
        assert str1.callmethod("concat", str2) == "Hellohello": "concat method fail (String)";
        println(str2.callmethod("capitalize"));
        assert str1.callmethod("getChar",3) == 'l' : "getChar method fail (String)";
        assert str2.callmethod("chop") == "hell" : "chop method fail (String)";
        str2.callmethod("chop_change");
        println(str2.read_dic("__default__"));
        assert str3.callmethod("empty") == true : "empty string method fail (String)";
        assert str2.callmethod("insert", 2, "l") == "helll" : "insert string method fail (String)";

        // Numeric Testing
        Instance num1 = new Instance(Global._RyNumeic, 1.5);
        Instance num2 = new Instance(Global._RyNumeic, -5);
        assert num1.callmethod("ceil") == 2 : "ceiling method fail (Numeric)";
        assert num1.callmethod("floor") == 1 : "flooring method fail (Numeric)";
        assert num2.callmethod("abs") == 5 : "ab method fail (Numeric)";
        assert num1.callmethod("add", num2) == -3.5 : "add method fail (Numeric)";
        assert num1.callmethod("minus", num2) == 6.5 : "add method fail (Numeric)";
        assert num1.callmethod("multiply", num2) == -7.5 : "add method fail (Numeric)";
        assert num1.callmethod("div", num2) == -0.3 : "add method fail (Numeric)";
        assert num2.callmethod("exponent", 2) == 25 : "exponent method fail (Numeric)";
    }
}