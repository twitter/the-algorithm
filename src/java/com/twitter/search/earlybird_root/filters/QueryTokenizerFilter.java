package com.twitter.search.earlybird_root.filters;

import java.util.concurrent.TimeUnit;
import javatwitter.inject.Inject;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.common_internal.tetwittert.version.PenguinVersionConfig;
import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.tracing.Trace;
import com.twitter.finagle.tracing.Tracing;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.metrics.SearchTimer;
import com.twitter.search.common.metrics.SearchTimerStats;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.search.earlybird_root.common.QueryParsingUtils;
import com.twitter.search.queryparser.parser.SerializedQueryParser;
import com.twitter.search.queryparser.parser.SerializedQueryParser.TokenizationOption;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.util.Duration;
import com.twitter.util.Future;

public class QueryTokenizerFilter etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {
  private static final String PREFItwitter = "query_tokenizer_";
  private static final SearchRateCounter SUCCESS_COUNTER =
      SearchRateCounter.etwitterport(PREFItwitter + "success");
  private static final SearchRateCounter FAILURE_COUNTER =
      SearchRateCounter.etwitterport(PREFItwitter + "error");
  private static final SearchRateCounter SKIPPED_COUNTER =
      SearchRateCounter.etwitterport(PREFItwitter + "skipped");
  private static final SearchTimerStats QUERY_TOKENIZER_TIME =
      SearchTimerStats.etwitterport(PREFItwitter + "time", TimeUnit.MILLISECONDS, false);

  private final TokenizationOption tokenizationOption;

  @Inject
  public QueryTokenizerFilter(PenguinVersionConfig penguinversions) {
    PenguinVersion[] supportedVersions = penguinversions
        .getSupportedVersions().toArray(new PenguinVersion[0]);
    tokenizationOption = new TokenizationOption(true, supportedVersions);
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {

    if (!requestContetwittert.getRequest().isRetokenizeSerializedQuery()
        || !requestContetwittert.getRequest().isSetSearchQuery()
        || !requestContetwittert.getRequest().getSearchQuery().isSetSerializedQuery()) {
      SKIPPED_COUNTER.increment();
      return service.apply(requestContetwittert);
    }

    SearchTimer timer = QUERY_TOKENIZER_TIME.startNewTimer();
    try {
      String serializedQuery = requestContetwittert.getRequest().getSearchQuery().getSerializedQuery();
      Query parsedQuery = reparseQuery(serializedQuery);
      SUCCESS_COUNTER.increment();
      return service.apply(EarlybirdRequestContetwittert.copyRequestContetwittert(requestContetwittert, parsedQuery));
    } catch (QueryParserEtwitterception e) {
      FAILURE_COUNTER.increment();
      return QueryParsingUtils.newClientErrorResponse(requestContetwittert.getRequest(), e);
    } finally {
      long elapsed = timer.stop();
      QUERY_TOKENIZER_TIME.timerIncrement(elapsed);
      Tracing trace = Trace.apply();
      if (trace.isActivelyTracing()) {
        trace.record(PREFItwitter + "time", Duration.fromMilliseconds(elapsed));
      }
    }
  }

  public Query reparseQuery(String serializedQuery) throws QueryParserEtwitterception {
    SerializedQueryParser parser = new SerializedQueryParser(tokenizationOption);
    return parser.parse(serializedQuery);
  }

  /**
   * Initializing the query parser can take many seconds. We initialize it at warmup so that
   * requests don't time out after we join the serverset. SEARCH-28801
   */
  public void performEtwitterpensiveInitialization() throws QueryParserEtwitterception {
    SerializedQueryParser queryParser = new SerializedQueryParser(tokenizationOption);

    // The Korean query parser takes a few seconds on it's own to initialize.
    String koreanQuery = "스포츠";
    queryParser.parse(koreanQuery);
  }
}
