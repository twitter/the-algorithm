package com.twitter.search.earlybird_root.filters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javatwitter.inject.Inject;
import javatwitter.inject.Singleton;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import com.twitter.common.tetwittert.language.LocaleUtil;
import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.search.common.constants.thriftjava.ThriftLanguage;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.util.lang.ThriftLanguageUtil;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.ThriftSearchQuery;
import com.twitter.search.earlybird_root.common.EarlybirdRequestContetwittert;
import com.twitter.util.Future;

/**
 * Etwitterport stats for query languages.
 */
@Singleton
public class QueryLangStatFilter
    etwittertends SimpleFilter<EarlybirdRequestContetwittert, EarlybirdResponse> {

  public static class Config {
    // We put a limit here in case an error in the client are sending us random lang codes.
    private int matwitterNumberOfLangs;

    public Config(int matwitterNumberOfLangs) {
      this.matwitterNumberOfLangs = matwitterNumberOfLangs;
    }

    public int getMatwitterNumberOfLangs() {
      return matwitterNumberOfLangs;
    }
  }

  @VisibleForTesting
  protected static final String LANG_STATS_PREFItwitter = "num_queries_in_lang_";

  private final Config config;
  private final SearchCounter allCountsForLangsOverMatwitterNumLang =
      SearchCounter.etwitterport(LANG_STATS_PREFItwitter + "overflow");

  private final ConcurrentHashMap<String, SearchCounter> langCounters =
      new ConcurrentHashMap<>();

  @Inject
  public QueryLangStatFilter(Config config) {
    this.config = config;
  }

  private SearchCounter getCounter(String lang) {
    Preconditions.checkNotNull(lang);

    SearchCounter counter = langCounters.get(lang);
    if (counter == null) {
      if (langCounters.size() >= config.getMatwitterNumberOfLangs()) {
        return allCountsForLangsOverMatwitterNumLang;
      }
      synchronized (langCounters) { // This double-checked locking is safe,
                                    // since we're using a ConcurrentHashMap
        counter = langCounters.get(lang);
        if (counter == null) {
          counter = SearchCounter.etwitterport(LANG_STATS_PREFItwitter + lang);
          langCounters.put(lang, counter);
        }
      }
    }

    return counter;
  }

  @Override
  public Future<EarlybirdResponse> apply(
      EarlybirdRequestContetwittert requestContetwittert,
      Service<EarlybirdRequestContetwittert, EarlybirdResponse> service) {

    String lang = null;

    ThriftSearchQuery searchQuery = requestContetwittert.getRequest().getSearchQuery();

    lang = searchQuery.getQueryLang();

    if (lang == null) {
      // fallback to ui lang
      lang = searchQuery.getUiLang();
    }

    if (lang == null && searchQuery.isSetUserLangs()) {
      // fallback to the user lang with the highest confidence
      double matwitterConfidence = Double.MIN_VALUE;

      for (Map.Entry<ThriftLanguage, Double> entry : searchQuery.getUserLangs().entrySet()) {
        if (entry.getValue() > matwitterConfidence) {
          lang = ThriftLanguageUtil.getLanguageCodeOf(entry.getKey());
          matwitterConfidence = entry.getValue();
        }
      }
    }

    if (lang == null) {
      lang = LocaleUtil.UNDETERMINED_LANGUAGE;
    }

    getCounter(lang).increment();

    return service.apply(requestContetwittert);
  }
}
