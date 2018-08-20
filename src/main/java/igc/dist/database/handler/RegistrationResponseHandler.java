package igc.dist.database.handler;

import com.google.protobuf.GeneratedMessageV3;
import igc.dist.proto.Register.RegistrationResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegistrationResponseHandler implements PacketHandler<RegistrationResponse> {

  @Override
  public void handle(RegistrationResponse packet, ChannelHandlerContext ctx) {
    log.info("database registered successfully");
  }
}
