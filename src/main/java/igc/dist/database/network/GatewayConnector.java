package igc.dist.database.network;

import igc.dist.proto.Register.RegistrationRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GatewayConnector extends ChannelHandlerAdapter {

  private final String host;
  private final int port;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);

    log.info("registering myself");
    ctx.writeAndFlush(RegistrationRequest.newBuilder()
        .setHost(host)
        .setPort(port)
        .build());
  }

}
