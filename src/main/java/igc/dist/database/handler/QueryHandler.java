package igc.dist.database.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import igc.dist.proto.Query.QueryRequest;
import igc.dist.proto.Query.QueryResponse;
import igc.dist.proto.Query.QueryType;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryHandler implements PacketHandler<QueryRequest> {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void handle(QueryRequest query, ChannelHandlerContext ctx) {
    if (query.getQueryType() == QueryType.READ) {
      var res = jdbcTemplate.queryForList(query.getQuery());

      try {
        var rawString = objectMapper.writeValueAsString(res);
        ctx.writeAndFlush(QueryResponse.newBuilder()
            .setResult(rawString)
            .build());

      } catch (JsonProcessingException e) {
        log.error("serialization error", e);
      }
    }
  }
}
