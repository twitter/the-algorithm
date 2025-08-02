package com.twitter.search.ingester.pipeline.wire;

import java.util.List;
import java.util.concurrent.EtwitterecutorService;
import javatwitter.annotation.Nullable;
import javatwitter.naming.Contetwittert;
import javatwitter.naming.InitialContetwittert;
import javatwitter.naming.NamingEtwitterception;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.thrift.TBase;

import com.twitter.common.util.Clock;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.decider.Decider;
import com.twitter.eventbus.client.EventBusSubscriber;
import com.twitter.finagle.mtls.authentication.ServiceIdentifier;
import com.twitter.finatra.kafka.producers.BlockingFinagleKafkaProducer;
import com.twitter.gizmoduck.thriftjava.UserService;
import com.twitter.metastore.client_v2.MetastoreClient;
import com.twitter.pink_floyd.thrift.Storer;
import com.twitter.search.common.partitioning.base.PartitionMappingManager;
import com.twitter.search.common.relevance.classifiers.TweetOffensiveEvaluator;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.ingester.pipeline.strato_fetchers.AudioSpaceCoreFetcher;
import com.twitter.search.ingester.pipeline.strato_fetchers.AudioSpaceParticipantsFetcher;
import com.twitter.search.ingester.pipeline.strato_fetchers.NamedEntityFetcher;
import com.twitter.search.ingester.pipeline.util.PipelineEtwitterceptionHandler;
import com.twitter.storage.client.manhattan.kv.JavaManhattanKVEndpoint;
import com.twitter.tweetypie.thriftjava.TweetService;
import com.twitter.util.Duration;
import com.twitter.util.Function;
import com.twitter.util.Future;

/**
 * An "injection module" that provides bindings for all ingester endpoints that we want to mock out
 * in tests.
 */
public abstract class WireModule {
  /** The JNDI property to which this module will be bound. */
  private static final String WIRE_MODULE_NAME = "";

  /** The root name of all properties specified in the twitter-naming-production.*.twitterml files. */
  public static final String JNDI_PIPELINE_ROOT = "";

  /**
   * (Re)binds the given wire module in JNDI.
   *
   * @param wireModule The wire module to bind in JNDI.
   * @throws NamingEtwitterception If the wire module cannot be bound in JNDI for some reason.
   */
  public static void bindWireModule(WireModule wireModule) throws NamingEtwitterception {
    Contetwittert jndiContetwittert = new InitialContetwittert();
    jndiContetwittert.rebind(WIRE_MODULE_NAME, wireModule);
  }

  /**
   * Returns the wire module bound in JNDI.
   *
   * @return The wire module bound in JNDI.
   * @throws NamingEtwitterception If there's no wire module bound in JNDI.
   */
  public static WireModule getWireModule() throws NamingEtwitterception {
    Contetwittert jndiContetwittert = new InitialContetwittert();
    return (WireModule) jndiContetwittert.lookup(WIRE_MODULE_NAME);
  }

  /**
   * Retrieves the service identifier needed for making mtls requests.
   * @return The service identifier for the current running service.
   */
  public abstract ServiceIdentifier getServiceIdentifier();

  /**
   * Creates a new {@code FinagleKafkaConsumer} with a specified consumer group ID.
   */
  public abstract <T> KafkaConsumer<Long, T> newKafkaConsumer(
      String kafkaClusterPath, Deserializer<T> deserializer, String clientId, String groupId,
      int matwitterPollRecords);

  /**
   * Creates a new {@code FinagleKafkaConsumer} with a specified consumer group ID.
   */
  public abstract <T> BlockingFinagleKafkaProducer<Long, T> newFinagleKafkaProducer(
      String kafkaClusterPath, Serializer<T> serializer, String clientId,
      @Nullable Class<? etwittertends Partitioner> partitionerClass);

  /**
   * Gets a TweetyPie client.
   *
   * @param tweetypieClientId Use this string as the client id.
   * @return A TweetyPie client
   * @throws NamingEtwitterception
   */
  public abstract TweetService.ServiceToClient getTweetyPieClient(String tweetypieClientId)
      throws NamingEtwitterception;

  /**
   * Gets a Gizmoduck client.
   *
   * @param clientId
   * @throws NamingEtwitterception
   */
  public abstract UserService.ServiceToClient getGizmoduckClient(String clientId)
      throws NamingEtwitterception;

  /**
   * Gets the ManhattanKVEndpoint that should be used for the ManhattanCodedLocationProvider
   *
   * @return the JavaManhattanKVEndpoint that we need for the ManhattanCodedLocationProvider
   * @throws NamingEtwitterception
   */
  public abstract JavaManhattanKVEndpoint getJavaManhattanKVEndpoint()
      throws NamingEtwitterception;

  /**
   * Returns the decider to be used by all stages.
   *
   * @return The decider to be used by all stages.
   */
  public abstract Decider getDecider();

  /**
   * Returns the partition ID to be used by all stages.
   *
   * @return The partition ID to be used by all stages.
   */
  public abstract int getPartition();


  /**
   * Returns the PipelineEtwitterceptionHandler instance to be used by all stages.
   *
   * @return The PipelineEtwitterceptionHandler instance to be used by all stages.
   * @throws NamingEtwitterception If building the PipelineEtwitterceptionHandler instance requires some
   *                         parameters, and those parameters were not bound in JNDI.
   */
  public abstract PipelineEtwitterceptionHandler getPipelineEtwitterceptionHandler();

  /**
   * Gets the PartitionMappingManager for the Kafka writer.
   *
   * @return a PartitionMappingManager
   */
  public abstract PartitionMappingManager getPartitionMappingManager();

  /**
   * Returns the Metastore client used by the UserPropertiesManager.
   *
   * @return A Metastore client.
   * @throws NamingEtwitterception
   */
  public abstract MetastoreClient getMetastoreClient() throws NamingEtwitterception;

  /**
   * Returns an EtwitterecutorService potentially backed by the specified number of threads.
   *
   * @param numThreads An advisory value with a suggestion for how large the threadpool should be.
   * @return an EtwitterecutorService that might be backed by some threads.
   * @throws NamingEtwitterception
   */
  public abstract EtwitterecutorService getThreadPool(int numThreads) throws NamingEtwitterception;

  /**
   * Returns the Storer interface to connect to Pink.
   *
   * @param requestTimeout The request timeout for the Pink client.
   * @param retries The number of Finagle retries.
   * @return a Storer.ServiceIface to connect to pink.
   *
   */
  public abstract Storer.ServiceIface getStorer(Duration requestTimeout, int retries)
      throws NamingEtwitterception;

  /**
   * Returns an EventBusSubscriber
   */
  public abstract <T etwittertends TBase<?, ?>> EventBusSubscriber<T> createEventBusSubscriber(
      Function<T, Future<?>> process,
      Class<T> thriftStructClass,
      String eventBusSubscriberId,
      int matwitterConcurrentEvents);

  /**
   * Returns a Clock.
   */
  public abstract Clock getClock();

  /**
   * Returns a TweetOffensiveEvaluator.
   */
  public abstract TweetOffensiveEvaluator getTweetOffensiveEvaluator();

  /**
   * Returns the cluster.
   */
  public abstract EarlybirdCluster getEarlybirdCluster() throws NamingEtwitterception;

  /**
   * Returns the current penguin version(s).
   */
  public abstract List<PenguinVersion> getPenguinVersions() throws NamingEtwitterception;

  /**
   * Returns updated penguin version(s) depending on decider availability.
   */
  public abstract List<PenguinVersion> getCurrentlyEnabledPenguinVersions();

  /**
   * Returns a named entities strato column fetcher.
   */
  public abstract NamedEntityFetcher getNamedEntityFetcher();

  /**
   * Returns audio space participants strato column fetcher.
   */
  public abstract AudioSpaceParticipantsFetcher getAudioSpaceParticipantsFetcher();

  /**
   * Returns audio space core strato column fetcher.
   */
  public abstract AudioSpaceCoreFetcher getAudioSpaceCoreFetcher();
}
