package net.youtunity.devathlon.api.net.util;

import io.netty.buffer.ByteBuf;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ByteBufUtils {

    public static String readString(ByteBuf buf) {

        int lenght = readVarInt(buf);
        byte bytes[] = new byte[lenght];
        buf.readBytes(bytes);

        return new String(bytes);
    }

    public static void writeString(String value, ByteBuf out) {

        byte[] bytes = value.getBytes();
        writeVarInt(bytes.length, out);
        out.writeBytes(value.getBytes());
    }

    public static int readVarInt(ByteBuf buf) {

        int value = 0;
        int size = 0;
        int b;

        while (((b = buf.readByte()) & 0x80) == 0x80) {
            value |= (b & 0x7F) << (size++ * 7);
            if(size > 5) {
                throw new RuntimeException("VarInt too long");
            }
        }

        return value;
    }

    public static void writeVarInt(int value, ByteBuf out) {

        while ((value & ~0x7F) != 0) {
            out.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }
}
