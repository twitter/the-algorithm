package com.twitter.search.common.relevance.classifiers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSource;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.text.language.LocaleUtil;
import com.twitter.common.text.token.TokenizedCharSequence;
import com.twitter.common.text.token.attribute.TokenType;
import com.twitter.common.util.Clock;
import com.twitter.common_internal.text.pipeline.TwitterNgramGenerator;
import com.twitter.common_internal.text.topic.BlacklistedTopics;
import com.twitter.common_internal.text.topic.BlacklistedTopics.FilterMode;
import com.twitter.common_internal.text.version.PenguinVersion;
import com.twitter.search.common.metrics.RelevanceStats;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.TweetTextFeatures;
import com.twitter.search.common.relevance.features.TweetTextQuality;
import com.twitter.search.common.util.io.periodic.PeriodicFileLoader;
import com.twitter.search.common.util.text.NormalizerHelper;
import com.twitter.search.common.util.text.TokenizerHelper;

/**
 * Determines if tweet text or username contains potentially offensive language.
 */
public class TweetOffensiveEvaluator extends TweetEvaluator {
  private static final Logger LOG = LoggerFactory.getLogger(TweetOffensiveEvaluator.class);

  private static final int MAX_OFFENSIVE_TERMS = 3;

  private final File filterDirectory;
  private static final File DEFAULT_FILTER_DIR = new File("");
  private static final String ADULT_TOKEN_FILE_NAME = "adult_tokens.txt";

  private static final ThreadLocal<TwitterNgramGenerator> NGRAM_GENERATOR_HOLDER =
      new ThreadLocal<TwitterNgramGenerator>() {
        @Override
        protected TwitterNgramGenerator initialValue() {
          // It'll generate ngrams from TokenizedCharSequence, which contains tokenization results,
          // so it doesn't matter which Penguin version to use here.
          return new TwitterNgramGenerator.Builder(PenguinVersion.PENGUIN_6)
              .setSize(1, MAX_OFFENSIVE_TERMS)
              .build();
        }
      };

  private final AtomicReference<BlacklistedTopics> offensiveTopics =
    new AtomicReference<>();
  private final AtomicReference<BlacklistedTopics> offensiveUsersTopics =
    new AtomicReference<>();

  private final AtomicReference<ByteSource> adultTokenFileContents = new AtomicReference<>();

  private final SearchCounter sensitiveTextCounter =
      RelevanceStats.exportLong("num_sensitive_text");

  public TweetOffensiveEvaluator() {
    this(DEFAULT_FILTER_DIR);
  }

  public TweetOffensiveEvaluator(
    File filterDirectory
  ) {
    this.filterDirectory = filterDirectory;
    adultTokenFileContents.set(BlacklistedTopics.getResource(
      BlacklistedTopics.DATA_PREFIX + ADULT_TOKEN_FILE_NAME));

    try {
      rebuildBlacklistedTopics();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
      new ThreadFactoryBuilder()
        .setNameFormat("offensive-evaluator-blacklist-reloader")
        .setDaemon(true)
        .build());
    initPeriodicFileLoader(adultTokenFileContents, ADULT_TOKEN_FILE_NAME, executor);
  }

  private void initPeriodicFileLoader(
    AtomicReference<ByteSource> byteSource,
    String fileName,
    ScheduledExecutorService executor) {
    File file = new File(filterDirectory, fileName);
    try {
      PeriodicFileLoader loader = new PeriodicFileLoader(
        "offensive-evaluator-" + fileName,
        file.getPath(),
        executor,
        Clock.SYSTEM_CLOCK) {
        @Override
        protected void accept(InputStream stream) throws IOException {
          byteSource.set(ByteSource.wrap(IOUtils.toByteArray(stream)));
          rebuildBlacklistedTopics();
        }
      };
      loader.init();
    } catch (Exception e) {
      // Not the end of the world if we couldn't load the file, we already loaded the resource.
      LOG.error("Could not load offensive topic filter " + fileName + " from ConfigBus", e);
    }
  }

  private void rebuildBlacklistedTopics() throws IOException {
    offensiveTopics.set(new BlacklistedTopics.Builder(false)
      .loadFilterFromSource(adultTokenFileContents.get(), FilterMode.EXACT)
      .build());
  }

  @Override
  public void evaluate(final TwitterMessage tweet) {
    BlacklistedTopics offensiveFilter = this.offensiveTopics.get();

    if (offensiveFilter == null) {
      return;
    }

    if (tweet.isSensitiveContent()) {
      sensitiveTextCounter.increment();
    }

    for (PenguinVersion penguinVersion : tweet.getSupportedPenguinVersions()) {
      TweetTextQuality textQuality = tweet.getTweetTextQuality(penguinVersion);

      if (tweet.isSensitiveContent()) {
        textQuality.addBoolQuality(TweetTextQuality.BooleanQualityType.SENSITIVE);
      }

      // Check if tweet has an offensive term
      if (isTweetOffensive(tweet, offensiveFilter, penguinVersion)) {
        SearchRateCounter offensiveTextCounter = RelevanceStats.exportRate(
            "num_offensive_text_" + penguinVersion.name().toLowerCase());
        offensiveTextCounter.increment();
        textQuality.addBoolQuality(TweetTextQuality.BooleanQualityType.OFFENSIVE);
      }
    }
  }

  private boolean isTweetOffensive(final TwitterMessage tweet,
                                   BlacklistedTopics offensiveFilter,
                                   PenguinVersion penguinVersion) {
    TweetTextFeatures textFeatures = tweet.getTweetTextFeatures(penguinVersion);

    boolean tweetHasOffensiveTerm = false;

    // Check for tweet text.
    List<TokenizedCharSequence> ngrams =
        NGRAM_GENERATOR_HOLDER.get().generateNgramsAsTokenizedCharSequence(
            textFeatures.getTokenSequence(), tweet.getLocale());
    for (TokenizedCharSequence ngram : ngrams) {
      // skip URL ngram
      if (!ngram.getTokensOf(TokenType.URL).isEmpty()) {
        continue;
      }
      String ngramStr = ngram.toString();
      if (!StringUtils.isBlank(ngramStr) && offensiveFilter.filter(ngramStr)) {
        tweetHasOffensiveTerm = true;
        break;
      }
    }

    // Due to some strangeness in Penguin, we don't get ngrams for tokens around "\n-" or "-\n"
    // in the original string, this made us miss some offensive words this way. Here we do another
    // pass of check using just the tokens generated by the tokenizer. (See SEARCHQUAL-8907)
    if (!tweetHasOffensiveTerm) {
      for (String ngramStr : textFeatures.getTokens()) {
        // skip URLs
        if (ngramStr.startsWith("http://") || ngramStr.startsWith("https://")) {
          continue;
        }
        if (!StringUtils.isBlank(ngramStr) && offensiveFilter.filter(ngramStr)) {
          tweetHasOffensiveTerm = true;
          break;
        }
      }
    }

    if (!tweetHasOffensiveTerm) {
      // check for resolved URLs
      String resolvedUrlsText =
          Joiner.on(" ").skipNulls().join(textFeatures.getResolvedUrlTokens());
      List<String> ngramStrs = NGRAM_GENERATOR_HOLDER.get().generateNgramsAsString(
          resolvedUrlsText, LocaleUtil.UNKNOWN);
      for (String ngram : ngramStrs) {
        if (!StringUtils.isBlank(ngram) && offensiveFilter.filter(ngram)) {
          tweetHasOffensiveTerm = true;
          break;
        }
      }
    }

    return tweetHasOffensiveTerm;
  }
}
