package com.twitter.search.ingester.pipeline.twitter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.EtwitterecutorService;
import javatwitter.naming.NamingEtwitterception;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import org.apache.commons.pipeline.StageEtwitterception;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.relevance.classifiers.TweetEvaluator;
import com.twitter.search.common.relevance.classifiers.TweetOffensiveEvaluator;
import com.twitter.search.common.relevance.classifiers.TweetTetwittertClassifier;
import com.twitter.search.common.relevance.classifiers.TweetTetwittertEvaluator;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.scorers.TweetTetwittertScorer;

@ConsumedTypes(TwitterMessage.class)
@ProducesConsumed
public class TetwittertQualityEvaluationWorkerStage etwittertends TwitterBaseStage
    <TwitterMessage, TwitterMessage> {
  private static final Logger LOG = LoggerFactory.getLogger(TetwittertQualityEvaluationWorkerStage.class);

  private static final int NUM_THREADS = 5;
  private static final long SLOW_TWEET_TIME_MILLIS = 1000;
  // based on the batched branch 3 elements in the queue times 200 tweets per batch.
  private static final int MAtwitter_QUEUE_SIZE = 100;
  private final BlockingQueue<TwitterMessage> messages =
      Queues.newLinkedBlockingQueue(MAtwitter_QUEUE_SIZE);

  private static final String DO_TEtwitterT_QUALITY_EVALUATION_DECIDER_KEY_TEMPLATE =
      "ingester_%s_do_tetwittert_quality_evaluation";

  private EtwitterecutorService etwitterecutorService = null;
  private SearchRateCounter unscoredTweetCounter;
  private TweetTetwittertClassifier classifier;
  private final TweetTetwittertScorer scorer = new TweetTetwittertScorer(null);
  // Defined as static so that ClassifierWorker thread can use it
  private static SearchRateCounter slowTweetCounter;
  private SearchRateCounter threadErrorCounter;
  private SearchRateCounter threadInterruptionCounter;
  private String deciderKey;

  @Override
  public void initStats() {
    super.initStats();
    innerSetupStats();
  }

  public SearchRateCounter getUnscoredTweetCounter() {
    return unscoredTweetCounter;
  }

  @Override
  protected void innerSetupStats() {
    threadErrorCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_thread_error");
    threadInterruptionCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_thread_interruption");
    unscoredTweetCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_tweets_unscored_count");
    slowTweetCounter = SearchRateCounter.etwitterport(
        getStageNamePrefitwitter() + "_tetwittert_quality_evaluation_slow_tweet_count");
    SearchCustomGauge.etwitterport(getStageNamePrefitwitter() + "_queue_size", messages::size);
  }

  @Override
  protected void doInnerPreprocess() throws StageEtwitterception, NamingEtwitterception {
    innerSetup();
    etwitterecutorService = wireModule.getThreadPool(NUM_THREADS);
    for (int i = 0; i < NUM_THREADS; i++) {
      etwitterecutorService.submit(
          new ClassifierWorker());
    }
    LOG.info("Initialized {} classfiers and scorers.", NUM_THREADS);
  }

  @Override
  protected void innerSetup() throws NamingEtwitterception {
    deciderKey = String.format(DO_TEtwitterT_QUALITY_EVALUATION_DECIDER_KEY_TEMPLATE,
        earlybirdCluster.getNameForStats());
    List<PenguinVersion> supportedPenguinVersions = wireModule.getPenguinVersions();
    TweetOffensiveEvaluator tweetOffensiveEvaluator = wireModule.getTweetOffensiveEvaluator();

    ImmutableList<TweetEvaluator> evaluators =
        ImmutableList.of(tweetOffensiveEvaluator, new TweetTetwittertEvaluator());
    classifier = new TweetTetwittertClassifier(
        evaluators,
        wireModule.getServiceIdentifier(),
        supportedPenguinVersions);
  }

  @Override
  public void innerProcess(Object obj) throws StageEtwitterception {
    if (!(obj instanceof TwitterMessage)) {
      LOG.error("Object is not a TwitterMessage object: {}", obj);
      return;
    }

    if (decider.isAvailable(deciderKey)) {
      TwitterMessage message = TwitterMessage.class.cast(obj);
      try {
        messages.put(message);
      } catch (InterruptedEtwitterception ie) {
        LOG.error("Interrupted etwitterception adding to the queue", ie);
      }
    } else {
      unscoredTweetCounter.increment();
      emitAndCount(obj);
    }
  }

  @Override
  protected TwitterMessage innerRunStageV2(TwitterMessage message) {
    if (decider.isAvailable(deciderKey)) {
      classifyAndScore(message);
    } else {
      unscoredTweetCounter.increment();
    }

    return message;
  }

  private void classifyAndScore(TwitterMessage message) {
    long startTime = clock.nowMillis();
    try {
      // The tweet signature computed here might not be correct, since we did not resolve the
      // tweet URLs yet. This is why BasicIndetwitteringConverter does not set the tweet signature
      // feature on the event it builds.
      //
      // We correct the tweet signature later in the ComputeTweetSignatureStage, and
      // DelayedIndetwitteringConverter sets this feature on the URL update event it creates.
      synchronized (this) {
        scorer.classifyAndScoreTweet(classifier, message);
      }
    } catch (Etwitterception e) {
      threadErrorCounter.increment();
      LOG.error("Uncaught etwitterception from classifyAndScoreTweet", e);
    } finally {
      long elapsedTime = clock.nowMillis() - startTime;
      if (elapsedTime > SLOW_TWEET_TIME_MILLIS) {
        LOG.warn("Took {}ms to classify and score tweet {}: {}",
            elapsedTime, message.getId(), message);
        slowTweetCounter.increment();
      }
    }
  }

  @Override
  public void innerPostprocess() {
    if (etwitterecutorService != null) {
      etwitterecutorService.shutdownNow();
    }
    etwitterecutorService = null;
  }

  private class ClassifierWorker implements Runnable {
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        TwitterMessage message;
        try {
          message = messages.take();
        } catch (InterruptedEtwitterception ie) {
          threadInterruptionCounter.increment();
          LOG.error("Interrupted etwitterception polling from the queue", ie);
          continue;
        }

        // We want to emit even if we couldn't score the tweet.
        classifyAndScore(message);
        emitAndCount(message);
      }
    }
  }
}

