package igc.dist.database.network;

import com.google.protobuf.GeneratedMessageV3;
import igc.dist.database.handler.PacketHandler;
import igc.dist.database.network.codec.ProtocolDecoder;
import igc.dist.database.network.codec.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseGatewayConnectorInitializer extends ChannelInitializer<SocketChannel> {

  private static final Map<Integer, GeneratedMessageV3> MESSAGES = PacketConfig.messages();
  private static final Map<Class, Integer> MESSAGE_IDS = PacketConfig.messageIds();

  private final ExecutorService packetHandlerExecutor = Executors.newFixedThreadPool(8);
  private final Map<Class, PacketHandler> handlers;

  @Override
  protected void initChannel(final SocketChannel ch) {
    var pipeline = ch.pipeline();
    pipeline
        .addLast("protocolDecoder", new ProtocolDecoder(MESSAGES))
        .addLast("protocolEncoder", new ProtocolEncoder(MESSAGE_IDS))
        .addLast("gatewayConnector", new GatewayConnector())
        .addLast("loginPacketHandler", new DatabasePacketHandler(packetHandlerExecutor, handlers));
  }
}
