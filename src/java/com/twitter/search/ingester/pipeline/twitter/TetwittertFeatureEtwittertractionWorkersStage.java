package com.twitter.search.ingester.pipeline.twitter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.EtwitterecutorService;
import javatwitter.naming.NamingEtwitterception;

import com.google.common.collect.Queues;

import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.tetwittert.TweetParser;
import com.twitter.search.ingester.pipeline.util.PipelineStageRuntimeEtwitterception;

@ConsumedTypes(TwitterMessage.class)
@ProducesConsumed
public class TetwittertFeatureEtwittertractionWorkersStage etwittertends TwitterBaseStage
    <TwitterMessage, TwitterMessage> {
  private static final Logger LOG =
      LoggerFactory.getLogger(TetwittertFeatureEtwittertractionWorkersStage.class);

  private static final int NUM_THREADS = 5;
  private static final int MAtwitter_QUEUE_SIZE = 100;
  private static final long SLOW_TWEET_TIME_MILLIS = 1000;
  private EtwitterecutorService etwitterecutorService = null;

  // define as static so that FeatureEtwittertractorWorker thread can use it
  private static SearchRateCounter slowTweetCounter;
  private SearchRateCounter threadErrorCounter;
  private SearchRateCounter threadInterruptionCounter;
  private final BlockingQueue<TwitterMessage> messageQueue =
      Queues.newLinkedBlockingQueue(MAtwitter_QUEUE_SIZE);
  private TweetParser tweetParser;

  @Override
  public void initStats() {
    super.initStats();
    innerSetupStats();
  }

  @Override
  protected void innerSetupStats() {
    slowTweetCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_feature_etwittertraction_slow_tweet_count");
    SearchCustomGauge.etwitterport(getStageNamePrefitwitter() + "_queue_size",
        messageQueue::size);
    threadErrorCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_thread_error");
    threadInterruptionCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_thread_interruption");
  }

  @Override
  protected void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    innerSetup();
    // anything threading related, we don't need in V2 as of yet.
    etwitterecutorService = wireModule.getThreadPool(NUM_THREADS);
    for (int i = 0; i < NUM_THREADS; ++i) {
      etwitterecutorService.submit(new FeatureEtwittertractorWorker());
    }
    LOG.info("Initialized {} parsers.", NUM_THREADS);
  }

  @Override
  protected void innerSetup() {
    tweetParser = new TweetParser();
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof TwitterMessage)) {
      LOG.error("Object is not a TwitterMessage object: {}", obj);
      return;
    }

    TwitterMessage message = TwitterMessage.class.cast(obj);
    try {
      messageQueue.put(message);
    } catch (InterruptedEtwitterception ie) {
      LOG.error("Interrupted etwitterception adding to the queue", ie);
    }
  }

  private boolean tryToParse(TwitterMessage message) {
    boolean isAbleToParse = false;
    long startTime = clock.nowMillis();
    // Parse tweet and merge the parsed out features into what we already have in the message.
    try {
      synchronized (this) {
        tweetParser.parseTweet(message, false, false);
      }
      // If parsing failed we don't need to pass the tweet down the pipeline.
      isAbleToParse = true;
    } catch (Etwitterception e) {
      threadErrorCounter.increment();
      LOG.error("Uncaught etwitterception from tweetParser.parseTweet()", e);
    } finally {
      long elapsedTime = clock.nowMillis() - startTime;
      if (elapsedTime > SLOW_TWEET_TIME_MILLIS) {
        LOG.debug("Took {}ms to parse tweet {}: {}", elapsedTime, message.getId(), message);
        slowTweetCounter.increment();
      }
    }
    return isAbleToParse;
  }

  @Override
  protected TwitterMessage innerRunStageV2(TwitterMessage message) {
    if (!tryToParse(message)) {
      throw new PipelineStageRuntimeEtwitterception("Failed to parse, not passing to netwittert stage.");
    }

    return message;
  }

  @Override
  public void innerPostprocess() {
    if (etwitterecutorService != null) {
      etwitterecutorService.shutdownNow();
    }
    etwitterecutorService = null;
  }

  private class FeatureEtwittertractorWorker implements Runnable {
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        TwitterMessage message = null;
        try {
          message = messageQueue.take();
        } catch (InterruptedEtwitterception ie) {
          threadInterruptionCounter.increment();
          LOG.error("Interrupted etwitterception polling from the queue", ie);
          continue;
        } finally {
          if (tryToParse(message)) {
            emitAndCount(message);
          }
        }
      }
    }
  }
}
