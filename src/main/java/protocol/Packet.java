package protocol;

public record Packet(int type, String text) {

    /**
     * Class which defines the attributes of a packet
     *
     * @param type Integer that defines the type of packet.
     * @param text String which a packet holds.
     */

    public Packet {
    }

    /**
     * @return String which holds the value held in a packet.
     */

    @Override
    public String text() {
        return text;
    }

    /**
     * @return The int type defined in a packet.
     */

    @Override
    public int type() {
        return type;
    }
}
