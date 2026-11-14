package protocol;

public class Protocol {
    /**
     * Class which contains the integer value of protocol types within packets
     */

    public static final int SET_NAME = 1;
    public static final int CHAT = 2;
    public static final int BROADCAST = 3;
    public static final int SYSTEM_MESSAGE = 4;


    /*
        [length = 8 bytes, int][type = 1 byte, byte][payload = string]
     */
}
