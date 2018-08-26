package igc.dist.database;

import igc.dist.database.network.DatabaseGatewayConnectorInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public abstract class AbstractServer implements Server {

  private final int port;
  private final int gatewayPort;
  private final String gatewayHost;
  private final EventLoopGroup bossGroup;
  private final EventLoopGroup workerGroup;
  private final ChannelInitializer<SocketChannel> serverInitializer;
  private final ChannelInitializer<SocketChannel> gatewayConnectorInitializer;
  private final ChannelHandler channelHandler;
  private final Class<? extends ServerChannel> serverChannel;

  @Override
  @PostConstruct
  public void start() throws InterruptedException {

    try {
      log.info("The server is going to start.");
      final ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
          .channel(serverChannel)
          .handler(channelHandler)
          .childHandler(serverInitializer);

      bootstrap.bind(port).sync();
      log.info("The server was started at {} port.", port);

      var connectFuture = new Bootstrap().group(workerGroup)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(gatewayConnectorInitializer)
          .connect(gatewayHost, gatewayPort);

      connectFuture.sync();
      log.info("Database was connected to gateway");
    } catch (final InterruptedException e) {
      log.error("The server failed to start", e);
      throw e;
    }
  }

  @Override
  @PreDestroy
  public void shutdown() {
    log.info("The server is going to shutdown.");
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
    log.info("The server was shutdown.");
  }
}
