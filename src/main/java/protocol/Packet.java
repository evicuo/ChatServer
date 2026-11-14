package protocol;

public class Packet {

    private final int type;
    private final String text;

    /**
     * Class which defines the attributes of a packet
     *
     * @param type Integer that defines the type of packet.
     * @param text String which a packet holds.
     */

    public Packet(int type, String text){
        this.type = type;
        this.text = text;
    }

    /**
     * @return String which holds the value held in a packet.
     */

    public String getText() {
        return text;
    }

    /**
     * @return The int type defined in a packet.
     */

    public int getType() {
        return type;
    }
}
