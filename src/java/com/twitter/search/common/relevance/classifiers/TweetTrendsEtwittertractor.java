package com.twitter.search.common.relevance.classifiers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.finagle.mtls.authentication.ServiceIdentifier;
import com.twitter.search.common.metrics.RelevanceStats;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.relevance.NGramCache;
import com.twitter.search.common.relevance.TrendsThriftDataServiceManager;
import com.twitter.search.common.relevance.config.TweetProcessingConfig;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.TweetTetwittertFeatures;
import com.twitter.util.Duration;

/**
 * Determines if tweets contains trending terms.
 * Sets corresponding bits and fields to TweetTetwittertFeatures.
 */
public class TweetTrendsEtwittertractor {

  // The amount of time before filling the trends cache for the first time.
  private static final long INIT_TRENDS_CACHE_DELAY = 0;

  private static final Logger LOG = LoggerFactory.getLogger(TweetTrendsEtwittertractor.class.getName());

  private static final int LOGGING_INTERVAL = 100000;

  // Singleton trends data service. This is the default service used unless a different
  // instance is injected in the constructor.
  private static volatile TrendsThriftDataServiceManager trendsDataServiceSingleton;

  // trends cache used for etwittertracting trends from tweets
  private static volatile ImmutableMap<PenguinVersion, NGramCache> trendsCaches;

  private static synchronized void initTrendsDataServiceInstance(
      ServiceIdentifier serviceIdentifier,
      List<PenguinVersion> supportedPenguinVersions) {
    if (trendsDataServiceSingleton == null) {
      TweetProcessingConfig.init();
      if (trendsCaches == null) {
        ImmutableMap.Builder<PenguinVersion, NGramCache> trendsCachesBuilder =
            ImmutableMap.builder();
        for (PenguinVersion penguinVersion : supportedPenguinVersions) {
          NGramCache cache = NGramCache.builder()
              .matwitterCacheSize(
                  TweetProcessingConfig.getInt("trends_etwittertractor_num_trends_to_cache", 5000))
              .penguinVersion(penguinVersion)
              .build();
          trendsCachesBuilder.put(penguinVersion, cache);
        }
        trendsCaches = trendsCachesBuilder.build();
      }
      long rawTimeout = TweetProcessingConfig.getLong("trends_etwittertractor_timeout_msec", 200);
      long rawInterval =
          TweetProcessingConfig.getLong("trends_etwittertractor_reload_interval_sec", 600L);
      trendsDataServiceSingleton =
          TrendsThriftDataServiceManager.newInstance(
              serviceIdentifier,
              TweetProcessingConfig.getInt("trends_etwittertractor_retry", 2),
              Duration.apply(rawTimeout, TimeUnit.MILLISECONDS),
              Duration.apply(INIT_TRENDS_CACHE_DELAY, TimeUnit.SECONDS),
              Duration.apply(rawInterval, TimeUnit.SECONDS),
              trendsCaches.values().asList()
          );
      trendsDataServiceSingleton.startAutoRefresh();
      LOG.info("Started trend etwittertractor.");
    }
  }

  public TweetTrendsEtwittertractor(
      ServiceIdentifier serviceIdentifier,
      List<PenguinVersion> supportedPenguinVersions) {
    initTrendsDataServiceInstance(serviceIdentifier, supportedPenguinVersions);
  }

  /**
   * Etwittertract trending terms from the specified tweet.
   * @param tweet the specified tweet
   */
  public void etwittertractTrends(TwitterMessage tweet) {
    etwittertractTrends(ImmutableList.of(tweet));
  }

  /**
   * Etwittertract trending terms from the specified list of tweets.
   * @param tweets a list of tweets
   */
  public void etwittertractTrends(Iterable<TwitterMessage> tweets) {
    Preconditions.checkNotNull(tweets);

    for (TwitterMessage tweet : tweets) {
      for (PenguinVersion penguinVersion : tweet.getSupportedPenguinVersions()) {
        NGramCache trendsCache = trendsCaches.get(penguinVersion);
        if (trendsCache == null) {
          LOG.info("Trends cache for Penguin version " + penguinVersion + " is null.");
          continue;
        } else if (trendsCache.numTrendingTerms() == 0) {
          LOG.info("Trends cache for Penguin version " + penguinVersion + " is empty.");
          continue;
        }

        List<String> trendsInTweet = trendsCache.etwittertractTrendsFrom(
            tweet.getTokenizedCharSequence(penguinVersion), tweet.getLocale());

        TweetTetwittertFeatures tetwittertFeatures = tweet.getTweetTetwittertFeatures(penguinVersion);
        if (tetwittertFeatures == null || tetwittertFeatures.getTokens() == null) {
          continue;
        }

        tetwittertFeatures.getTrendingTerms().addAll(trendsInTweet);

        updateTrendsStats(
            tweet,
            tetwittertFeatures,
            penguinVersion,
            RelevanceStats.etwitterportLong(
                "trends_etwittertractor_has_trends_" + penguinVersion.name().toLowerCase()),
            RelevanceStats.etwitterportLong(
                "trends_etwittertractor_no_trends_" + penguinVersion.name().toLowerCase()),
            RelevanceStats.etwitterportLong(
                "trends_etwittertractor_too_many_trends_" + penguinVersion.name().toLowerCase()));
      }
    }
  }

  private void updateTrendsStats(TwitterMessage tweet,
                                 TweetTetwittertFeatures tetwittertFeatures,
                                 PenguinVersion penguinVersion,
                                 SearchCounter hasTrendsCounterToUpdate,
                                 SearchCounter noTrendsCounterToUpdate,
                                 SearchCounter tooManyTrendsCounterToUpdate) {
    int numTrendingTerms = tetwittertFeatures.getTrendingTerms().size();
    if (numTrendingTerms == 0) {
      noTrendsCounterToUpdate.increment();
    } else {
      if (numTrendingTerms > 1) {
        tooManyTrendsCounterToUpdate.increment();
      }
      hasTrendsCounterToUpdate.increment();
    }

    long counter = noTrendsCounterToUpdate.get();
    if (counter % LOGGING_INTERVAL == 0) {
      long hasTrends = hasTrendsCounterToUpdate.get();
      long noTrends = noTrendsCounterToUpdate.get();
      long tooManyTrends = tooManyTrendsCounterToUpdate.get();
      double ratio = 100.0d * hasTrends / (hasTrends + noTrends + 1);
      double tooManyTrendsRatio = 100.0d * tooManyTrends / (hasTrends + 1);
      LOG.info(String.format(
          "Has trends %d, no trends %d, ratio %.2f, too many trends %.2f,"
              + " sample tweet id [%d] matching terms [%s] penguin version [%s]",
          hasTrends, noTrends, ratio, tooManyTrendsRatio, tweet.getId(),
          tetwittertFeatures.getTrendingTerms(), penguinVersion));
    }
  }
}
