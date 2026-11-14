package protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class PacketIO {

    /**
     * Takes a packet and writes to output stream in byte-form
     * @param out Output stream to be written to
     * @param packet Packet to be encoded and sent
     * @throws IOException Occurs when output stream not writeable
     */
    public static void write(DataOutputStream out, Packet packet) throws IOException {
        byte[] payload = packet.text().getBytes(StandardCharsets.UTF_8);

        int length = 1 + payload.length;
        out.writeInt(length);
        out.writeByte(packet.type());
        out.write(payload);
        out.flush();
    }

    /**
     * Takes a stream of bytes and returns a packet
     * @return Optional, nullable packet object
     */
    public static Optional<Packet> read(DataInputStream in) throws IOException {
        int length = in.readInt();
        if(length == 0 || length > 65536) {
            return Optional.empty();
        }
        byte[] body = in.readNBytes(length);
        // java uses signed bytes - using bitwise operator flips from twos complement to unsigned
        int type = body[0] & 0xFF;
        String text = new String(body, 1, body.length - 1, StandardCharsets.UTF_8);
        return Optional.of(new Packet(type, text));
    }
}
