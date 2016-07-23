package net.youtunity.devathlon.api.net.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by thecrealm on 23.07.16.
 */
public class LenghtDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

        while (byteBuf.readableBytes() > 0) {
            byteBuf.markReaderIndex();

            if(byteBuf.readableBytes() < 4) {
                return;
            }

            int readableBytes = byteBuf.readInt();
            if(byteBuf.readableBytes() < readableBytes) {
                byteBuf.resetReaderIndex();
                return;
            }

            out.add(byteBuf.copy(byteBuf.readerIndex(), readableBytes));
            byteBuf.skipBytes(readableBytes);
        }
    }
}
