package igc.dist.database.handler;

import com.google.protobuf.GeneratedMessageV3;
import igc.dist.proto.Connection.ChooseDatabase;
import igc.dist.proto.Connection.MessageAccepted;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ChooseDatabaseHandler implements PacketHandler<ChooseDatabase> {

  public static final Map<String, Status> STATUS_MAP = new ConcurrentHashMap<>();

  @Override
  public void handle(ChooseDatabase packet, ChannelHandlerContext ctx) {
    STATUS_MAP.putIfAbsent(packet.getToken(), Status.WAITING);

    ctx.writeAndFlush(MessageAccepted.newBuilder().build());
  }

  public enum Status {
    WAITING,
    USED,
    EXPIRED
  }
}
