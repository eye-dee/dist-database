package igc.dist.database.handler;

import static igc.dist.database.handler.ChooseDatabaseHandler.STATUS_MAP;

import igc.dist.database.handler.ChooseDatabaseHandler.Status;
import igc.dist.proto.Connection.CreateConnection;
import igc.dist.proto.Connection.MessageAccepted;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateConnectionHandler implements PacketHandler<CreateConnection> {

  public static final Map<String, ChannelHandlerContext> USER_CONNECTIONS = new ConcurrentHashMap<>();

  @Override
  public void handle(CreateConnection packet, ChannelHandlerContext ctx) {
    var newStatus = STATUS_MAP.computeIfPresent(packet.getToken(), (t, s) -> {
      if (s == Status.WAITING) {
        USER_CONNECTIONS.putIfAbsent(t, ctx);
        return Status.USED;
      } else {
        return s;
      }
    });

    if (newStatus == Status.EXPIRED) {
      log.info("user with token {} closed", packet.getToken());
      ctx.close();
    } else {
      ctx.writeAndFlush(MessageAccepted.getDefaultInstance());
      log.info("user with token {} connected", packet.getToken());
    }
  }
}
