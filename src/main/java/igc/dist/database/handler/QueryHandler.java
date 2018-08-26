package igc.dist.database.handler;

import static igc.dist.database.handler.CreateConnectionHandler.USER_CONNECTIONS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import igc.dist.proto.Query.InternalQueryRequest;
import igc.dist.proto.Query.QueryResponse;
import igc.dist.proto.Query.QueryType;
import io.netty.channel.ChannelHandlerContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryHandler implements PacketHandler<InternalQueryRequest> {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void handle(InternalQueryRequest query, ChannelHandlerContext ctx) {
    var token = query.getToken();
    var context = Optional.ofNullable(USER_CONNECTIONS.get(token));

    if (query.getQueryType() == QueryType.READ) {
      var res = jdbcTemplate.queryForList(query.getQuery());

      try {
        var rawString = objectMapper.writeValueAsString(res);
        context.ifPresentOrElse(c -> c.writeAndFlush(QueryResponse.newBuilder()
            .setResult(rawString)
            .build()), () -> log.info("null context"));

      } catch (JsonProcessingException e) {
        log.error("serialization error", e);
      }
    }
  }
}
