package igc.dist.database.network;

import igc.dist.proto.Register.RegistrationRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GatewayConnector extends ChannelHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);

    log.info("registering myself");
    ctx.writeAndFlush(RegistrationRequest.getDefaultInstance());
  }

}
