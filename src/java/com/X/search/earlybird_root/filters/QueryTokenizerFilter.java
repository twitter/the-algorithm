package com.X.search.earlybird_root.filters;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import com.X.common_internal.text.version.PenguinVersion;
import com.X.common_internal.text.version.PenguinVersionConfig;
import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.finagle.tracing.Trace;
import com.X.finagle.tracing.Tracing;
import com.X.search.common.metrics.SearchRateCounter;
import com.X.search.common.metrics.SearchTimer;
import com.X.search.common.metrics.SearchTimerStats;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestContext;
import com.X.search.earlybird_root.common.QueryParsingUtils;
import com.X.search.queryparser.parser.SerializedQueryParser;
import com.X.search.queryparser.parser.SerializedQueryParser.TokenizationOption;
import com.X.search.queryparser.query.Query;
import com.X.search.queryparser.query.QueryParserException;
import com.X.util.Duration;
import com.X.util.Future;

public class QueryTokenizerFilter extends SimpleFilter<EarlybirdRequestContext, EarlybirdResponse> {
  private static final String PREFIX = "query_tokenizer_";
  private static final SearchRateCounter SUCCESS_COUNTER =
      SearchRateCounter.export(PREFIX + "success");
  private static final SearchRateCounter FAILURE_COUNTER =
      SearchRateCounter.export(PREFIX + "error");
  private static final SearchRateCounter SKIPPED_COUNTER =
      SearchRateCounter.export(PREFIX + "skipped");
  private static final SearchTimerStats QUERY_TOKENIZER_TIME =
      SearchTimerStats.export(PREFIX + "time", TimeUnit.MILLISECONDS, false);

  private final TokenizationOption tokenizationOption;

  @Inject
  public QueryTokenizerFilter(PenguinVersionConfig penguinversions) {
    PenguinVersion[] supportedVersions = penguinversions
        .getSupportedVersions().toArray(new PenguinVersion[0]);
    tokenizationOption = new TokenizationOption(true, supportedVersions);
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContext requestContext,
      Service<EarlybirdRequestContext, EarlybirdResponse> service) {

    if (!requestContext.getRequest().isRetokenizeSerializedQuery()
        || !requestContext.getRequest().isSetSearchQuery()
        || !requestContext.getRequest().getSearchQuery().isSetSerializedQuery()) {
      SKIPPED_COUNTER.increment();
      return service.apply(requestContext);
    }

    SearchTimer timer = QUERY_TOKENIZER_TIME.startNewTimer();
    try {
      String serializedQuery = requestContext.getRequest().getSearchQuery().getSerializedQuery();
      Query parsedQuery = reparseQuery(serializedQuery);
      SUCCESS_COUNTER.increment();
      return service.apply(EarlybirdRequestContext.copyRequestContext(requestContext, parsedQuery));
    } catch (QueryParserException e) {
      FAILURE_COUNTER.increment();
      return QueryParsingUtils.newClientErrorResponse(requestContext.getRequest(), e);
    } finally {
      long elapsed = timer.stop();
      QUERY_TOKENIZER_TIME.timerIncrement(elapsed);
      Tracing trace = Trace.apply();
      if (trace.isActivelyTracing()) {
        trace.record(PREFIX + "time", Duration.fromMilliseconds(elapsed));
      }
    }
  }

  public Query reparseQuery(String serializedQuery) throws QueryParserException {
    SerializedQueryParser parser = new SerializedQueryParser(tokenizationOption);
    return parser.parse(serializedQuery);
  }

  /**
   * Initializing the query parser can take many seconds. We initialize it at warmup so that
   * requests don't time out after we join the serverset. SEARCH-28801
   */
  public void performExpensiveInitialization() throws QueryParserException {
    SerializedQueryParser queryParser = new SerializedQueryParser(tokenizationOption);

    // The Korean query parser takes a few seconds on it's own to initialize.
    String koreanQuery = "스포츠";
    queryParser.parse(koreanQuery);
  }
}
