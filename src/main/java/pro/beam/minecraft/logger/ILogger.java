package pro.beam.minecraft.logger;

public interface ILogger {
	void log(Level l, String msg);

	enum Level {
		NORMAL, URGENT,
	}
}
