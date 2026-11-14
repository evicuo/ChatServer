package exceptions;

public class UnexpectedProtocolTypeException extends Exception {
    private final int expectedType;
    private final int receivedType;

    /**
     * Exception occurs when packet received is of the unexpected type when parsing
     *
     * @param expectedType - The type expected to be retrieved from packet
     * @param receivedType - The type that was retrieved from packet
     *
     */
    public UnexpectedProtocolTypeException(int expectedType, int receivedType){
        this.expectedType = expectedType;
        this.receivedType = receivedType;
    }
}
