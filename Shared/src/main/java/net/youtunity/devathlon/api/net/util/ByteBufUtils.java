package net.youtunity.devathlon.api.net.util;

import io.netty.buffer.ByteBuf;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ByteBufUtils {

    public static String readString(ByteBuf buf) {

        int lenght = buf.readInt();
        byte bytes[] = new byte[lenght];
        buf.readBytes(bytes);

        return new String(bytes);
    }

    public static void writeString(String value, ByteBuf out) {

        byte[] bytes = value.getBytes();
        out.writeInt(bytes.length);
        out.writeBytes(value.getBytes());
    }
}
