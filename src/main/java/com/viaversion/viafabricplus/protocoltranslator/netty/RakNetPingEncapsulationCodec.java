package com.viaversion.viafabricplus.protocoltranslator.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.net.InetSocketAddress;
import java.util.List;
import org.cloudburstmc.netty.channel.raknet.RakConstants;
import org.cloudburstmc.netty.channel.raknet.RakPing;
import org.cloudburstmc.netty.channel.raknet.RakPong;

public final class RakNetPingEncapsulationCodec extends MessageToMessageCodec<RakPong, ByteBuf> {

    public static final String NAME = "viabedrock-ping-encapsulation";

    private final InetSocketAddress remoteAddress;

    public RakNetPingEncapsulationCodec(final InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        final int packetId = in.readUnsignedByte();

        if (packetId == RakConstants.ID_UNCONNECTED_PING) {
            out.add(new RakPing(in.readLong(), this.remoteAddress));
        } else {
            ctx.close();
            throw new IllegalStateException("Unexpected packet ID: " + packetId);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, RakPong in, List<Object> out) {
        if (!this.remoteAddress.equals(in.getSender())) {
            ctx.close();
            throw new IllegalStateException("Received pong from unexpected address: " + in.getSender());
        }

        final ByteBuf buf = ctx.alloc().buffer();
        buf.writeByte(RakConstants.ID_UNCONNECTED_PONG);
        buf.writeLong(in.getPingTime());
        buf.writeLong(in.getGuid());
        buf.writeBytes(RakConstants.DEFAULT_UNCONNECTED_MAGIC);
        buf.writeShort(in.getPongData().readableBytes());
        buf.writeBytes(in.getPongData());
        out.add(buf);
    }

}
