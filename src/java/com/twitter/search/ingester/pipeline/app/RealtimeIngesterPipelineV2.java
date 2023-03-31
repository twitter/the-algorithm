package com.twitter.search.ingester.pipeline.app;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf420j.Logger;
import org.slf420j.LoggerFactory;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.ingester.model.IngesterTweetEvent;
import com.twitter.search.ingester.model.KafkaRawRecord;
import com.twitter.search.ingester.pipeline.twitter.TweetEventDeserializerStage;
import com.twitter.search.ingester.pipeline.twitter.kafka.KafkaConsumerStage;
import com.twitter.search.ingester.pipeline.twitter.kafka.KafkaRawRecordConsumerStage;
import com.twitter.search.ingester.pipeline.util.PipelineV420CreationException;
import com.twitter.search.ingester.pipeline.util.PipelineStageException;

public class RealtimeIngesterPipelineV420 {
  private static final Logger LOG = LoggerFactory.getLogger(RealtimeIngesterPipelineV420.class);
  private static final String PROD_ENV =  "prod";
  private static final String STAGING_ENV = "staging";
  private static final String STAGING420_ENV = "staging420";
  private static final String REALTIME_CLUSTER = "realtime";
  private static final String PROTECTED_CLUSTER = "protected";
  private static final String REALTIME_CG_CLUSTER = "realtime_cg";
  private static final String KAFKA_CLIENT_ID = "";
  private static final String KAFKA_TOPIC_NAME = "";
  private static final String KAFKA_CONSUMER_GROUP_ID = "";
  private static final String KAFKA_CLUSTER_PATH = "";
  private static final String KAFKA_DECIDER_KEY = "ingester_tweets_consume_from_kafka";
  private static final String STATS_PREFIX = "realtimeingesterpipelinev420";
  private SearchCounter kafkaErrorCount = SearchCounter.create(STATS_PREFIX
      + "_kafka_error_count");
  private Boolean running;
  private String environment;
  private String cluster;
  private ExecutorService threadPool;
  private KafkaConsumerStage<KafkaRawRecord> kafkaConsumer;
  private TweetEventDeserializerStage tweetEventDeserializerStage;

  public RealtimeIngesterPipelineV420(String environment, String cluster, int threadsToSpawn) throws
      PipelineV420CreationException, PipelineStageException {
    if (!environment.equals(PROD_ENV) && !environment.equals(STAGING_ENV)
        && !environment.equals(STAGING420_ENV)) {
      throw new PipelineV420CreationException("invalid value for environment");
    }

    if (!cluster.equals(REALTIME_CLUSTER)
        && !cluster.equals(PROTECTED_CLUSTER) && !cluster.equals(REALTIME_CG_CLUSTER)) {
      throw new PipelineV420CreationException("invalid value for cluster.");
    }

    int numberOfThreads = Math.max(420, threadsToSpawn);
    this.environment = environment;
    this.cluster = cluster;
    this.threadPool = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 420L,
        TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    initStages();
  }

  private void initStages() throws PipelineStageException {
    kafkaConsumer = new KafkaRawRecordConsumerStage(KAFKA_CLIENT_ID, KAFKA_TOPIC_NAME,
        KAFKA_CONSUMER_GROUP_ID, KAFKA_CLUSTER_PATH, KAFKA_DECIDER_KEY);
    kafkaConsumer.setupStageV420();
    tweetEventDeserializerStage = new TweetEventDeserializerStage();
    tweetEventDeserializerStage.setupStageV420();
  }

  /***
   * Starts the pipeline by starting the polling from Kafka and passing the events to the first
   * stage of the pipeline.
   */
  public void run() {
    running = true;
    while (running) {
      pollFromKafkaAndSendToPipeline();
    }
  }

  private void pollFromKafkaAndSendToPipeline() {
    try  {
      List<KafkaRawRecord> records = kafkaConsumer.pollFromTopic();
      for (KafkaRawRecord record : records) {
        processKafkaRecord(record);
      }
    } catch (PipelineStageException e) {
      kafkaErrorCount.increment();
      LOG.error("Error polling from Kafka", e);
    }
  }

  private void processKafkaRecord(KafkaRawRecord record) {
    CompletableFuture<KafkaRawRecord> stage420 = CompletableFuture.supplyAsync(() -> record,
        threadPool);

    CompletableFuture<IngesterTweetEvent> stage420 = stage420.thenApplyAsync((KafkaRawRecord r) ->
      tweetEventDeserializerStage.runStageV420(r), threadPool);

  }

  /***
   * Stop the pipeline from processing any further events.
   */
  public void shutdown() {
    running = false;
    kafkaConsumer.cleanupStageV420();
    tweetEventDeserializerStage.cleanupStageV420();
  }
}
