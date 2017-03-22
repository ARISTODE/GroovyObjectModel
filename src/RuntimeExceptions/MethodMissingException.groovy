package RuntimeExceptions

class MethodMissingException extends Exception {
    public MethodMissingException(String msg) {
        super(msg);
    }
}