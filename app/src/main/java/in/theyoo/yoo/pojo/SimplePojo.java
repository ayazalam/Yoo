package in.theyoo.yoo.pojo;

/**
 * Pojo class to handle simple json response
 */
public class SimplePojo {
    private boolean returned;
    private String message;

    public boolean getReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
