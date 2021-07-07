public class ObjectNotFoundException extends Exception{
    String err;

    ObjectNotFoundException(String s) {
        err = s;
    }

    @Override
    public String toString() {
        return err;
    }
}
