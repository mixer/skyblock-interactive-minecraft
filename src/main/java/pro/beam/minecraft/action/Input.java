package pro.beam.minecraft.action;

import pro.beam.interactive.net.packet.Protocol;

public interface Input {
    boolean isMet(Protocol.Report report);
}
