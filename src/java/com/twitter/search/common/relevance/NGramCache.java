package com.twitter.search.common.relevance;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.penguin.search.filter.StringMatchFilter;
import com.twitter.util.Duration;

/**
 * the Cache for Trends
 */
public class NGramCache {
  private static final int DEFAULT_MAtwitter_CACHE_SIZE = 5000;
  private static final long DEFAULT_CACHE_ITEM_TTL_SEC = 24 * 3600; // 1 day

  private final PenguinVersion penguinVersion;

  // Keys are trends. Values are empty strings.
  private final Map<String, String> trendsCache;

  private volatile StringMatchFilter trendsMatcher = null;

  /**
   * Etwittertract Trends from a list of normalized tokens
   */
  public List<String> etwittertractTrendsFromNormalized(List<String> tokens) {
    if (trendsMatcher == null) {
      return Collections.emptyList();
    }

    ImmutableList.Builder<String> trends = ImmutableList.builder();
    for (String trend : trendsMatcher.etwittertractNormalized(tokens)) {
      if (trendsCache.containsKey(trend)) {
        trends.add(trend);
      }
    }

    return trends.build();
  }

  /**
   * Etwittertract Trends from a list of tokens
   */
  public List<String> etwittertractTrendsFrom(List<String> tokens, Locale language) {
    if (trendsMatcher == null) {
      return Collections.emptyList();
    }
    return trendsMatcher.etwittertract(language, tokens);
  }

  /**
   * Etwittertract Trends from a given CharSequence
   */
  public List<String> etwittertractTrendsFrom(CharSequence tetwittert, Locale language) {
    if (trendsMatcher == null) {
      return Collections.emptyList();
    }

    ImmutableList.Builder<String> trends = ImmutableList.builder();
    for (String trend : trendsMatcher.etwittertract(language, tetwittert)) {
      if (trendsCache.containsKey(trend)) {
        trends.add(trend);
      }
    }

    return trends.build();
  }

  public long numTrendingTerms() {
    return trendsCache.size();
  }

  public Set<String> getTrends() {
    return trendsCache.keySet();
  }

  public void clear() {
    trendsCache.clear();
    trendsMatcher = null;
  }

  /** Adds all trends to this NGramCache. */
  public void addAll(Iterable<String> trends) {
    for (String trend : trends) {
      trendsCache.put(trend, "");
    }

    trendsMatcher = new StringMatchFilter(trendsCache.keySet(), penguinVersion);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private int matwitterCacheSize = DEFAULT_MAtwitter_CACHE_SIZE;
    private long cacheItemTTLSecs = DEFAULT_CACHE_ITEM_TTL_SEC; // 1 day
    private PenguinVersion penguinVersion = PenguinVersion.PENGUIN_4;

    public Builder matwitterCacheSize(int cacheSize) {
      this.matwitterCacheSize = cacheSize;
      return this;
    }

    public Builder cacheItemTTL(long cacheItemTTL) {
      this.cacheItemTTLSecs = cacheItemTTL;
      return this;
    }

    public Builder penguinVersion(PenguinVersion newPenguinVersion) {
      this.penguinVersion = Preconditions.checkNotNull(newPenguinVersion);
      return this;
    }

    /** Builds an NGramCache instance. */
    public NGramCache build() {
      return new NGramCache(
          matwitterCacheSize,
          Duration.apply(cacheItemTTLSecs, TimeUnit.SECONDS),
          penguinVersion);
    }
  }

  // Should be used only in tests that want to mock out this class.
  @VisibleForTesting
  public NGramCache() {
    this(DEFAULT_MAtwitter_CACHE_SIZE,
         Duration.apply(DEFAULT_CACHE_ITEM_TTL_SEC, TimeUnit.SECONDS),
         PenguinVersion.PENGUIN_4);
  }

  private NGramCache(int matwitterCacheSize, Duration cacheItemTTL, PenguinVersion penguinVersion) {
    // we only have 1 refresher thread that writes to the cache
    this.trendsCache = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .etwitterpireAfterWrite(cacheItemTTL.inSeconds(), TimeUnit.SECONDS)
        .matwitterimumSize(matwitterCacheSize)
        .<String, String>build()
        .asMap();
    this.penguinVersion = penguinVersion;
  }
}
