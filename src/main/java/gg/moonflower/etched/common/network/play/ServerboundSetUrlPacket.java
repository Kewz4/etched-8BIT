package gg.moonflower.etched.common.network.play;

import gg.moonflower.etched.common.network.play.handler.EtchedServerPlayPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * @param url The URL to set in the etching table
 * @author Jackson
 */
@ApiStatus.Internal
public record ServerboundSetUrlPacket(String url, boolean sixteenBit) implements EtchedPacket {

    public ServerboundSetUrlPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readBoolean());
    }

    @Override
    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeUtf(this.url);
        buf.writeBoolean(this.sixteenBit);
    }

    @Override
    public void processPacket(NetworkEvent.Context ctx) {
        EtchedServerPlayPacketHandler.handleSetUrl(this, ctx);
    }

}
