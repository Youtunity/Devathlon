package net.youtunity.devathlon.daemon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.youtunity.devathlon.api.net.util.ByteBufUtils;

/**
 * Created by thecrealm on 23.07.16.
 */
public class Bootstrap {

    public static void main(String[] args) {

        Daemon daemon = Daemon.getInstance();
        daemon.init(args);


        //Sleep Loop
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
