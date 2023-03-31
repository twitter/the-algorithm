package com.twitter.search.earlybird.common.config;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import com.twitter.app.Flag;
import com.twitter.app.Flaggable;
import com.twitter.app.Flags;
import com.twitter.finagle.mtls.authentication.ServiceIdentifier;

/**
 * Stateless class that represents an Earlybird property that can be specified by a command line
 * flag.
 * <p>
 * This is a regular Java class instead of enum to have a generic type.
 *
 * @param <T>
 */
public final class EarlybirdProperty<T> {

  private static final class PropertyType<T> {

    private static final PropertyType<Boolean> BOOLEAN = new PropertyType<>(
        Flaggable.ofJavaBoolean(), EarlybirdConfig::getBool, EarlybirdConfig::getBool);

    private static final PropertyType<Integer> INT = new PropertyType<>(
        Flaggable.ofJavaInteger(), EarlybirdConfig::getInt, EarlybirdConfig::getInt);

    private static final PropertyType<String> STRING = new PropertyType<>(
        Flaggable.ofString(), EarlybirdConfig::getString, EarlybirdConfig::getString);

    private final Flaggable<T> flaggable;
    private final Function<String, T> getter;
    private final BiFunction<String, T, T> getterWithDefault;

    private PropertyType(Flaggable<T> flaggable, Function<String, T> getter,
                         BiFunction<String, T, T> getterWithDefault) {
      this.flaggable = flaggable;
      this.getter = getter;
      this.getterWithDefault = getterWithDefault;
    }
  }

  public static final EarlybirdProperty<String> PENGUIN_VERSION =
      new EarlybirdProperty<>(
          "penguin_version",
          "The penguin version to index.",
          PropertyType.STRING,
          false);

  public static final EarlybirdProperty<Integer> THRIFT_PORT = new EarlybirdProperty<>(
      "thrift_port",
      "override thrift port from config file",
      PropertyType.INT,
      false);

  public static final EarlybirdProperty<Integer> WARMUP_THRIFT_PORT = new EarlybirdProperty<>(
      "warmup_thrift_port",
      "override warmup thrift port from config file",
      PropertyType.INT,
      false);

  public static final EarlybirdProperty<Integer> SEARCHER_THREADS = new EarlybirdProperty<>(
      "searcher_threads",
      "override number of searcher threads from config file",
      PropertyType.INT,
      false);

  public static final EarlybirdProperty<String> EARLYBIRD_TIER = new EarlybirdProperty<>(
      "earlybird_tier",
      "the earlybird tier (e.g. tier1), used on Aurora",
      PropertyType.STRING,
      true);

  public static final EarlybirdProperty<Integer> REPLICA_ID = new EarlybirdProperty<>(
      "replica_id",
      "the ID in a partition, used on Aurora",
      PropertyType.INT,
      true);

  public static final EarlybirdProperty<Integer> PARTITION_ID = new EarlybirdProperty<>(
      "partition_id",
      "partition ID, used on Aurora",
      PropertyType.INT,
      true);

  public static final EarlybirdProperty<Integer> NUM_PARTITIONS = new EarlybirdProperty<>(
      "num_partitions",
      "number of partitions, used on Aurora",
      PropertyType.INT,
      true);

  public static final EarlybirdProperty<Integer> NUM_INSTANCES = new EarlybirdProperty<>(
      "num_instances",
      "number of instances in the job, used on Aurora",
      PropertyType.INT,
      true);

  public static final EarlybirdProperty<Integer> SERVING_TIMESLICES = new EarlybirdProperty<>(
      "serving_timeslices",
      "number of time slices to serve, used on Aurora",
      PropertyType.INT,
      true);

  public static final EarlybirdProperty<String> ROLE = new EarlybirdProperty<>(
      "role",
      "Role in the service path of Earlybird",
      PropertyType.STRING,
      true,
      true);

  public static final EarlybirdProperty<String> EARLYBIRD_NAME = new EarlybirdProperty<>(
      "earlybird_name",
      "Name in the service path of Earlybird without hash partition suffix",
      PropertyType.STRING,
      true,
      true);

  public static final EarlybirdProperty<String> ENV = new EarlybirdProperty<>(
      "env",
      "Environment in the service path of Earlybird",
      PropertyType.STRING,
      true,
      true);

  public static final EarlybirdProperty<String> ZONE = new EarlybirdProperty<>(
      "zone",
      "Zone (data center) in the service path of Earlybird",
      PropertyType.STRING,
      true,
      true);

  public static final EarlybirdProperty<String> DL_URI = new EarlybirdProperty<>(
      "dl_uri",
      "DistributedLog URI for default DL reader",
      PropertyType.STRING,
      false);

  public static final EarlybirdProperty<String> USER_UPDATES_DL_URI = new EarlybirdProperty<>(
      "user_updates_dl_uri",
      "DistributedLog URI for user updates DL reader",
      PropertyType.STRING,
      false);

  public static final EarlybirdProperty<String> ANTISOCIAL_USERUPDATES_DL_STREAM =
      new EarlybirdProperty<>(
          "antisocial_userupdates_dl_stream",
          "DL stream name for antisocial user updates without DL version suffix",
          PropertyType.STRING,
          false);

  public static final EarlybirdProperty<String> ZK_APP_ROOT = new EarlybirdProperty<>(
      "zk_app_root",
      "SZooKeeper base root path for this application",
      PropertyType.STRING,
      true);

  public static final EarlybirdProperty<Boolean> SEGMENT_LOAD_FROM_HDFS_ENABLED =
      new EarlybirdProperty<>(
          "segment_load_from_hdfs_enabled",
          "Whether to load segment data from HDFS",
          PropertyType.BOOLEAN,
          false);

  public static final EarlybirdProperty<Boolean> SEGMENT_FLUSH_TO_HDFS_ENABLED =
      new EarlybirdProperty<>(
          "segment_flush_to_hdfs_enabled",
          "Whether to flush segment data to HDFS",
          PropertyType.BOOLEAN,
          false);

  public static final EarlybirdProperty<String> HDFS_SEGMENT_SYNC_DIR = new EarlybirdProperty<>(
      "hdfs_segment_sync_dir",
      "HDFS directory to sync segment data",
      PropertyType.STRING,
      false);

  public static final EarlybirdProperty<String> HDFS_SEGMENT_UPLOAD_DIR = new EarlybirdProperty<>(
      "hdfs_segment_upload_dir",
      "HDFS directory to upload segment data",
      PropertyType.STRING,
      false);

  public static final EarlybirdProperty<Boolean> ARCHIVE_DAILY_STATUS_BATCH_FLUSHING_ENABLED =
      new EarlybirdProperty<>(
          "archive_daily_status_batch_flushing_enabled",
          "Whether to enable archive daily status batch flushing",
          PropertyType.BOOLEAN,
          false);

  public static final EarlybirdProperty<String> HDFS_INDEX_SYNC_DIR = new EarlybirdProperty<>(
      "hdfs_index_sync_dir",
      "HDFS directory to sync index data",
      PropertyType.STRING,
      true);

  public static final EarlybirdProperty<Boolean> READ_INDEX_FROM_PROD_LOCATION =
      new EarlybirdProperty<>(
      "read_index_from_prod_location",
      "Read index from prod to speed up startup on staging / loadtest",
      PropertyType.BOOLEAN,
      false);

  public static final EarlybirdProperty<Boolean> USE_DECIDER_OVERLAY = new EarlybirdProperty<>(
      "use_decider_overlay",
      "Whether to use decider overlay",
      PropertyType.BOOLEAN,
      false);

  public static final EarlybirdProperty<String> DECIDER_OVERLAY_CONFIG = new EarlybirdProperty<>(
      "decider_overlay_config",
      "Path to decider overlay config",
      PropertyType.STRING,
      false);

  public static final EarlybirdProperty<Integer> MAX_CONCURRENT_SEGMENT_INDEXERS =
      new EarlybirdProperty<>(
        "max_concurrent_segment_indexers",
        "Maximum number of segments indexed concurrently",
        PropertyType.INT,
        false);

  public static final EarlybirdProperty<Boolean> TF_MODELS_ENABLED =
      new EarlybirdProperty<>(
        "tf_models_enabled",
        "Whether tensorflow models should be loaded",
        PropertyType.BOOLEAN,
        false);

  public static final EarlybirdProperty<String> TF_MODELS_CONFIG_PATH =
      new EarlybirdProperty<>(
        "tf_models_config_path",
        "The configuration path of the yaml file containing the list of tensorflow models to load.",
        PropertyType.STRING,
        false);

  public static final EarlybirdProperty<Integer> TF_INTER_OP_THREADS =
      new EarlybirdProperty<>(
        "tf_inter_op_threads",
        "How many tensorflow inter op threads to use. See TF documentation for more information.",
        PropertyType.INT,
        false);

  public static final EarlybirdProperty<Integer> TF_INTRA_OP_THREADS =
      new EarlybirdProperty<>(
        "tf_intra_op_threads",
        "How many tensorflow intra op threads to use. See TF documentation for more information.",
        PropertyType.INT,
        false);

  public static final EarlybirdProperty<Integer> MAX_ALLOWED_REPLICAS_NOT_IN_SERVER_SET =
      new EarlybirdProperty<>(
          "max_allowed_replicas_not_in_server_set",
          "How many replicas are allowed to be missing from the Earlybird server set.",
          PropertyType.INT,
          false);

  public static final EarlybirdProperty<Boolean> CHECK_NUM_REPLICAS_IN_SERVER_SET =
      new EarlybirdProperty<>(
          "check_num_replicas_in_server_set",
          "Whether CoordinatedEarlybirdActions should check the number of alive replicas",
          PropertyType.BOOLEAN,
          false);

  public static final EarlybirdProperty<Integer> MAX_QUEUE_SIZE =
      new EarlybirdProperty<>(
          "max_queue_size",
          "Maximum size of searcher worker executor queue. If <= 0 queue is unbounded.",
          PropertyType.INT,
          false);

  public static final EarlybirdProperty<String> KAFKA_ENV =
      new EarlybirdProperty<>(
          "kafka_env",
          "The environment to use for kafka topics.",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<String> KAFKA_PATH =
      new EarlybirdProperty<>(
          "kafka_path",
          "Wily path to the Search kafka cluster.",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<String> TWEET_EVENTS_KAFKA_PATH =
      new EarlybirdProperty<>(
          "tweet_events_kafka_path",
          "Wily path to the tweet-events kafka cluster.",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<String> USER_UPDATES_KAFKA_TOPIC =
      new EarlybirdProperty<>(
          "user_updates_topic",
          "Name of the Kafka topic that contain user updates.",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<String> USER_SCRUB_GEO_KAFKA_TOPIC =
      new EarlybirdProperty<>(
          "user_scrub_geo_topic",
          "Name of the Kafka topic that contain UserScrubGeoEvents.",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<String> EARLYBIRD_SCRUB_GEN =
      new EarlybirdProperty<>(
          "earlybird_scrub_gen",
          "SCRUB_GEN TO DEPLOY",
          PropertyType.STRING,
          false);
  public static final EarlybirdProperty<Boolean> CONSUME_GEO_SCRUB_EVENTS =
      new EarlybirdProperty<>(
        "consume_geo_scrub_events",
        "Whether to consume user scrub geo events or not",
        PropertyType.BOOLEAN,
        false);

  private static final List<EarlybirdProperty<?>> ALL_PROPERTIES =
      Arrays.stream(EarlybirdProperty.class.getDeclaredFields())
          .filter(field ->
              (field.getModifiers() & Modifier.STATIC) > 0
                && field.getType() == EarlybirdProperty.class)
          .map(field -> {
            try {
              return (EarlybirdProperty<?>) field.get(EarlybirdProperty.class);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          })
          .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

  public static ServiceIdentifier getServiceIdentifier() {
    return new ServiceIdentifier(
        ROLE.get(),
        EARLYBIRD_NAME.get(),
        ENV.get(),
        ZONE.get());
  }

  private final String name;
  private final String help;
  private final PropertyType<T> type;
  private final boolean requiredOnAurora;
  private final boolean requiredOnDedicated;

  private EarlybirdProperty(String name, String help, PropertyType<T> type,
                            boolean requiredOnAurora) {
    this(name, help, type, requiredOnAurora, false);
  }

  private EarlybirdProperty(String name, String help, PropertyType<T> type,
                            boolean requiredOnAurora, boolean requiredOnDedicated) {
    this.name = name;
    this.help = help;
    this.type = type;
    this.requiredOnAurora = requiredOnAurora;
    this.requiredOnDedicated = requiredOnDedicated;
  }

  public String name() {
    return name;
  }

  public boolean isRequiredOnAurora() {
    return requiredOnAurora;
  }

  public boolean isRequiredOnDedicated() {
    return requiredOnDedicated;
  }

  public Flag<T> createFlag(Flags flags) {
    return flags.createMandatory(name, help, null, type.flaggable);
  }

  public T get() {
    return type.getter.apply(name);
  }

  public T get(T devaultValue) {
    return type.getterWithDefault.apply(name, devaultValue);
  }

  public static EarlybirdProperty[] values() {
    return ALL_PROPERTIES.toArray(new EarlybirdProperty[0]);
  }
}
