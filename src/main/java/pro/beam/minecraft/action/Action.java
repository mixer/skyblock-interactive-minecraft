package pro.beam.minecraft.action;

import pro.beam.interactive.net.packet.Protocol;

public interface Action {
	void take(Protocol.Report report);
}
