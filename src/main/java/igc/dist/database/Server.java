package igc.dist.database;

public interface Server {

  void start() throws InterruptedException;

  void shutdown();
}
