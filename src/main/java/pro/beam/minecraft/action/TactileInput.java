package pro.beam.minecraft.action;

import pro.beam.interactive.net.packet.Protocol;

public class TactileInput implements Input {
    protected final int code;
    protected final double threshold;

    public TactileInput(int code, double threshold) {
        this.code = code;
        this.threshold = threshold;
    }

    @Override
    public boolean isMet(Protocol.Report report) {
        for (Protocol.Report.TactileInfo info : report.getTactileList()) {
            if ((info.getId() == this.code) && (info.getPressFrequency() > 0)) {
                return true;
            }
        }

        return false;
    }

    public int code() {
        return this.code;
    }
}
