package igc.dist.database;

import igc.dist.database.network.DatabaseServerInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseServer extends AbstractServer {

    public DatabaseServer(@Value("${server.login-server.port:7777}") final int port,
                       final DatabaseServerInitializer databaseServerInitializer) {
        super(port, new NioEventLoopGroup(1), new NioEventLoopGroup(),
                databaseServerInitializer, new LoggingHandler(LogLevel.INFO), NioServerSocketChannel.class);
    }

}
