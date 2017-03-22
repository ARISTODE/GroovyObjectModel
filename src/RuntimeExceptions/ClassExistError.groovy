package RuntimeExceptions

class ClassExistError extends Exception {
    public ClassExistError(String msg) {
        super(msg);
    }
}