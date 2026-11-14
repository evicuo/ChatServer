package protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketIO {

    public static void write(DataOutputStream out, Packet packet) throws IOException {
        byte[] payload = packet.getText().getBytes(StandardCharsets.UTF_8);

        int length = 1+payload.length;
        out.writeInt(length);
        out.writeByte(packet.getType());
        out.write(payload);
        out.flush();

    }

    /**
     * Takes a stream of bytes and returns
     */
    public static Packet read(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] body = in.readNBytes(length);
        // java uses signed bytes - using bitwise operator flips from twos complement to unsigned
        int type = body[0] & 0xFF;
        String text = new String(body, 1, body.length-1, StandardCharsets.UTF_8);
        return new Packet(type, text);
    }
}
