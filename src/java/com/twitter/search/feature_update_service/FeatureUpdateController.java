package com.twitter.search.feature_update_service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javatwitter.inject.Inject;
import javatwitter.inject.Named;

import scala.runtime.BotwitteredUnit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.decider.Decider;
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception;
import com.twitter.finagle.thrift.ClientId;
import com.twitter.finatra.kafka.producers.BlockingFinagleKafkaProducer;
import com.twitter.inject.annotations.Flag;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.indetwittering.thriftjava.ThriftVersionedEvents;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEvent;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteringEventType;
import com.twitter.search.feature_update_service.modules.EarlybirdUtilModule;
import com.twitter.search.feature_update_service.modules.FinagleKafkaProducerModule;
import com.twitter.search.feature_update_service.stats.FeatureUpdateStats;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateRequest;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateResponse;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateResponseCode;
import com.twitter.search.feature_update_service.thriftjava.FeatureUpdateService;
import com.twitter.search.feature_update_service.util.FeatureUpdateValidator;
import com.twitter.search.ingester.model.IngesterThriftVersionedEvents;
import com.twitter.tweetypie.thriftjava.GetTweetFieldsOptions;
import com.twitter.tweetypie.thriftjava.GetTweetFieldsRequest;
import com.twitter.tweetypie.thriftjava.TweetInclude;
import com.twitter.tweetypie.thriftjava.TweetService;
import com.twitter.tweetypie.thriftjava.TweetVisibilityPolicy;
import com.twitter.util.EtwitterecutorServiceFuturePool;
import com.twitter.util.Function;
import com.twitter.util.Future;
import com.twitter.util.Futures;

import static com.twitter.tweetypie.thriftjava.Tweet._Fields.CORE_DATA;

public class FeatureUpdateController implements FeatureUpdateService.ServiceIface {
  private static final Logger LOG = LoggerFactory.getLogger(FeatureUpdateController.class);
  private static final Logger REQUEST_LOG =
      LoggerFactory.getLogger("feature_update_service_requests");
  private static final String KAFKA_SEND_COUNT_FORMAT = "kafka_%s_partition_%d_send_count";
  private static final String WRITE_TO_KAFKA_DECIDER_KEY = "write_events_to_kafka_update_events";
  private static final String WRITE_TO_KAFKA_DECIDER_KEY_REALTIME_CG =
          "write_events_to_kafka_update_events_realtime_cg";

  private final SearchRateCounter droppedKafkaUpdateEvents =
      SearchRateCounter.etwitterport("dropped_kafka_update_events");

  private final SearchRateCounter droppedKafkaUpdateEventsRealtimeCg =
          SearchRateCounter.etwitterport("dropped_kafka_update_events_realtime_cg");
  private final Clock clock;
  private final Decider decider;
  private final BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducer;
  private final BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducerRealtimeCg;

  private final List<PenguinVersion> penguinVersions;
  private final FeatureUpdateStats stats;
  private final String kafkaUpdateEventsTopicName;
  private final String kafkaUpdateEventsTopicNameRealtimeCg;
  private final EtwitterecutorServiceFuturePool futurePool;
  private final TweetService.ServiceIface tweetService;

  @Inject
  public FeatureUpdateController(
      Clock clock,
      Decider decider,
      @Named("KafkaProducer")
      BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducer,
      @Named("KafkaProducerRealtimeCg")
      BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> kafkaProducerRealtimeCg,
      @Flag(EarlybirdUtilModule.PENGUIN_VERSIONS_FLAG) String penguinVersions,
      FeatureUpdateStats stats,
      @Flag(FinagleKafkaProducerModule.KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG)
      String kafkaUpdateEventsTopicName,
      @Flag(FinagleKafkaProducerModule.KAFKA_TOPIC_NAME_UPDATE_EVENTS_FLAG_REALTIME_CG)
      String kafkaUpdateEventsTopicNameRealtimeCg,
      EtwitterecutorServiceFuturePool futurePool,
      TweetService.ServiceIface tweetService
  ) {
    this.clock = clock;
    this.decider = decider;
    this.kafkaProducer = kafkaProducer;
    this.kafkaProducerRealtimeCg = kafkaProducerRealtimeCg;
    this.penguinVersions = getPenguinVersions(penguinVersions);
    this.stats = stats;
    this.kafkaUpdateEventsTopicName = kafkaUpdateEventsTopicName;
    this.kafkaUpdateEventsTopicNameRealtimeCg = kafkaUpdateEventsTopicNameRealtimeCg;
    this.futurePool = futurePool;
    this.tweetService = tweetService;
  }

  @Override
  public Future<FeatureUpdateResponse> process(FeatureUpdateRequest featureUpdate) {
    long requestStartTimeMillis = clock.nowMillis();

    // Etwitterport overall and per-client request rate stats
    final String requestClientId;
    if (featureUpdate.getRequestClientId() != null
        && !featureUpdate.getRequestClientId().isEmpty()) {
      requestClientId = featureUpdate.getRequestClientId();
    } else if (ClientId.current().nonEmpty()) {
      requestClientId =  ClientId.current().get().name();
    } else {
      requestClientId = "unknown";
    }
    stats.clientRequest(requestClientId);
    REQUEST_LOG.info("{} {}", requestClientId, featureUpdate);

    FeatureUpdateResponse errorResponse = FeatureUpdateValidator.validate(featureUpdate);
    if (errorResponse != null) {
      stats.clientResponse(requestClientId, errorResponse.getResponseCode());
      LOG.warn("client error: clientID {} - reason: {}",
          requestClientId, errorResponse.getDetailMessage());
      return Future.value(errorResponse);
    }

    ThriftIndetwitteringEvent event = featureUpdate.getEvent();
    return writeToKafka(event, requestStartTimeMillis)
        .map(responsesList -> {
          stats.clientResponse(requestClientId, FeatureUpdateResponseCode.SUCCESS);
          // only when both Realtime & RealtimeCG succeed, then it will return a success flag
          return new FeatureUpdateResponse(FeatureUpdateResponseCode.SUCCESS);
        })
        .handle(Function.func(throwable -> {
          FeatureUpdateResponseCode responseCode;
          // if either Realtime or RealtimeCG throws an etwitterception, it will return a failure
          if (throwable instanceof ClientDiscardedRequestEtwitterception) {
            responseCode = FeatureUpdateResponseCode.CLIENT_CANCEL_ERROR;
            LOG.info("ClientDiscardedRequestEtwitterception received from client: " + requestClientId,
                throwable);
          } else {
            responseCode = FeatureUpdateResponseCode.TRANSIENT_ERROR;
            LOG.error("Error occurred while writing to output stream: "
                + kafkaUpdateEventsTopicName + ", "
                + kafkaUpdateEventsTopicNameRealtimeCg, throwable);
          }
          stats.clientResponse(requestClientId, responseCode);
          return new FeatureUpdateResponse(responseCode)
              .setDetailMessage(throwable.getMessage());
        }));
  }

  /**
   * In writeToKafka(), we use Futures.collect() to aggregate results for two RPC calls
   * Futures.collect() means that if either one of the Future fails then it will return an Etwitterception
   * only when both Realtime & RealtimeCG succeed, then it will return a success flag
   * The FeatureUpdateResponse is more like an ACK message, and the upstream (feature update ingester)
   * will not be affected much even if it failed (as long as the kafka message is written)
   */
  private Future<List<BotwitteredUnit>> writeToKafka(ThriftIndetwitteringEvent event,
                                               long requestStartTimeMillis) {
    return Futures.collect(Lists.newArrayList(
        writeToKafkaInternal(event, WRITE_TO_KAFKA_DECIDER_KEY, droppedKafkaUpdateEvents,
            kafkaUpdateEventsTopicName, -1, kafkaProducer),
        Futures.flatten(getUserId(event.getUid()).map(
            userId -> writeToKafkaInternal(event, WRITE_TO_KAFKA_DECIDER_KEY_REALTIME_CG,
            droppedKafkaUpdateEventsRealtimeCg,
            kafkaUpdateEventsTopicNameRealtimeCg, userId, kafkaProducerRealtimeCg)))));

  }

  private Future<BotwitteredUnit> writeToKafkaInternal(ThriftIndetwitteringEvent event, String deciderKey,
     SearchRateCounter droppedStats, String topicName, long userId,
     BlockingFinagleKafkaProducer<Long, ThriftVersionedEvents> producer) {
    if (!DeciderUtil.isAvailableForRandomRecipient(decider, deciderKey)) {
      droppedStats.increment();
      return Future.Unit();
    }

    ProducerRecord<Long, ThriftVersionedEvents> producerRecord = new ProducerRecord<>(
            topicName,
            convertToThriftVersionedEvents(userId, event));

    try {
      return Futures.flatten(futurePool.apply(() ->
              producer.send(producerRecord)
                      .map(record -> {
                        SearchCounter.etwitterport(String.format(
                          KAFKA_SEND_COUNT_FORMAT, record.topic(), record.partition())).increment();
                        return BotwitteredUnit.UNIT;
                      })));
    } catch (Etwitterception e) {
      return Future.etwitterception(e);
    }
  }

  private List<PenguinVersion> getPenguinVersions(String penguinVersionsStr) {
    String[] tokens = penguinVersionsStr.split("\\s*,\\s*");
    List<PenguinVersion> listOfPenguinVersions = Lists.newArrayListWithCapacity(tokens.length);
    for (String token : tokens) {
      listOfPenguinVersions.add(PenguinVersion.valueOf(token.toUpperCase()));
    }
    LOG.info(String.format("Using Penguin Versions: %s", listOfPenguinVersions));
    return listOfPenguinVersions;
  }

  private Future<Long> getUserId(long tweetId) {
    TweetInclude tweetInclude = new TweetInclude();
    tweetInclude.setTweetFieldId(CORE_DATA.getThriftFieldId());
    GetTweetFieldsOptions getTweetFieldsOptions = new GetTweetFieldsOptions().setTweet_includes(
        Collections.singleton(tweetInclude)).setVisibilityPolicy(
        TweetVisibilityPolicy.NO_FILTERING);
    GetTweetFieldsRequest getTweetFieldsRequest = new GetTweetFieldsRequest().setTweetIds(
        Arrays.asList(tweetId)).setOptions(getTweetFieldsOptions);
    try {
      return tweetService.get_tweet_fields(getTweetFieldsRequest).map(
          tweetFieldsResults -> tweetFieldsResults.get(
              0).tweetResult.getFound().tweet.core_data.user_id);
    } catch (Etwitterception e) {
      return Future.etwitterception(e);
    }
  }

  private ThriftVersionedEvents convertToThriftVersionedEvents(
      long userId, ThriftIndetwitteringEvent event) {
    ThriftIndetwitteringEvent thriftIndetwitteringEvent = event.deepCopy()
        .setEventType(ThriftIndetwitteringEventType.PARTIAL_UPDATE);

    ImmutableMap.Builder<Byte, ThriftIndetwitteringEvent> versionedEventsBuilder =
        new ImmutableMap.Builder<>();
    for (PenguinVersion penguinVersion : penguinVersions) {
      versionedEventsBuilder.put(penguinVersion.getByteValue(), thriftIndetwitteringEvent);
    }

    IngesterThriftVersionedEvents thriftVersionedEvents =
        new IngesterThriftVersionedEvents(userId, versionedEventsBuilder.build());
    thriftVersionedEvents.setId(thriftIndetwitteringEvent.getUid());
    return thriftVersionedEvents;
  }
}
