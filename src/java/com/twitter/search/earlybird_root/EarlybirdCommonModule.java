package com.twitter.search.earlybird_root;

import javatwitter.annotation.Nullable;
import javatwitter.inject.Named;
import javatwitter.inject.Singleton;

import scala.PartialFunction;

import com.google.inject.Provides;

import org.apache.thrift.protocol.TProtocolFactory;

import com.twitter.app.Flag;
import com.twitter.app.Flaggable;
import com.twitter.common.util.Clock;
import com.twitter.finagle.Service;
import com.twitter.finagle.mtls.authorization.server.MtlsServerSessionTrackerFilter;
import com.twitter.finagle.service.ReqRep;
import com.twitter.finagle.service.ResponseClass;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.finagle.thrift.RichServerParam;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.dark.DarkProtwittery;
import com.twitter.search.common.dark.ResolverProtwittery;
import com.twitter.search.common.partitioning.zookeeper.SearchZkClient;
import com.twitter.search.common.root.PartitionConfig;
import com.twitter.search.common.root.RemoteClientBuilder;
import com.twitter.search.common.root.RootClientServiceBuilder;
import com.twitter.search.common.root.SearchRootModule;
import com.twitter.search.common.root.ServerSetsConfig;
import com.twitter.search.common.util.zookeeper.ZooKeeperProtwittery;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.twitter.search.earlybird_root.filters.PreCacheRequestTypeCountFilter;
import com.twitter.search.earlybird_root.filters.QueryLangStatFilter;

/**
 * Provides common bindings.
 */
public class EarlybirdCommonModule etwittertends TwitterModule {
  static final String NAMED_ALT_CLIENT = "alt_client";
  static final String NAMED_EtwitterP_CLUSTER_CLIENT = "etwitterp_cluster_client";

  private final Flag<String> altZkRoleFlag = createFlag(
      "alt_zk_role",
      "",
      "The alternative ZooKeeper role",
      Flaggable.ofString());
  private final Flag<String> altZkClientEnvFlag = createFlag(
      "alt_zk_client_env",
      "",
      "The alternative zk client environment",
      Flaggable.ofString());
  private final Flag<String> altPartitionZkPathFlag = createFlag(
      "alt_partition_zk_path",
      "",
      "The alternative client partition zk path",
      Flaggable.ofString());

  @Override
  public void configure() {
    bind(InitializeFilter.class).in(Singleton.class);
    bind(PreCacheRequestTypeCountFilter.class).in(Singleton.class);

    bind(Clock.class).toInstance(Clock.SYSTEM_CLOCK);
    bind(QueryLangStatFilter.Config.class).toInstance(new QueryLangStatFilter.Config(100));
  }

  // Used in SearchRootModule.
  @Provides
  @Singleton
  PartialFunction<ReqRep, ResponseClass> provideResponseClassifier() {
    return new RootResponseClassifier();
  }

  @Provides
  @Singleton
  Service<byte[], byte[]> providesByteService(
      EarlybirdService.ServiceIface svc,
      DarkProtwittery<ThriftClientRequest, byte[]> darkProtwittery,
      TProtocolFactory protocolFactory) {
    return darkProtwittery.toFilter().andThen(
        new EarlybirdService.Service(
            svc, new RichServerParam(protocolFactory, SearchRootModule.SCROOGE_BUFFER_SIZE)));
  }

  @Provides
  @Singleton
  @Named(SearchRootModule.NAMED_SERVICE_INTERFACE)
  Class providesServiceInterface() {
    return EarlybirdService.ServiceIface.class;
  }

  @Provides
  @Singleton
  ZooKeeperProtwittery provideZookeeperClient() {
    return SearchZkClient.getSZooKeeperClient();
  }

  @Provides
  @Singleton
  EarlybirdFeatureSchemaMerger provideFeatureSchemaMerger() {
    return new EarlybirdFeatureSchemaMerger();
  }

  @Provides
  @Singleton
  @Nullable
  @Named(NAMED_ALT_CLIENT)
  ServerSetsConfig provideAltServerSetsConfig() {
    if (!altZkRoleFlag.isDefined() || !altZkClientEnvFlag.isDefined()) {
      return null;
    }

    return new ServerSetsConfig(altZkRoleFlag.apply(), altZkClientEnvFlag.apply());
  }

  @Provides
  @Singleton
  @Nullable
  @Named(NAMED_ALT_CLIENT)
  PartitionConfig provideAltPartitionConfig(PartitionConfig defaultPartitionConfig) {
    if (!altPartitionZkPathFlag.isDefined()) {
      return null;
    }

    return new PartitionConfig(
        defaultPartitionConfig.getNumPartitions(), altPartitionZkPathFlag.apply());
  }

  @Provides
  @Singleton
  @Nullable
  @Named(NAMED_ALT_CLIENT)
  RootClientServiceBuilder<EarlybirdService.ServiceIface> provideAltRootClientServiceBuilder(
      @Named(NAMED_ALT_CLIENT) @Nullable ServerSetsConfig serverSetsConfig,
      @Named(SearchRootModule.NAMED_SERVICE_INTERFACE) Class serviceIface,
      ResolverProtwittery resolverProtwittery,
      RemoteClientBuilder<EarlybirdService.ServiceIface> remoteClientBuilder) {
    if (serverSetsConfig == null) {
      return null;
    }

    return new RootClientServiceBuilder<>(
        serverSetsConfig, serviceIface, resolverProtwittery, remoteClientBuilder);
  }

  @Provides
  @Singleton
  @Named(NAMED_EtwitterP_CLUSTER_CLIENT)
  RootClientServiceBuilder<EarlybirdService.ServiceIface> provideEtwitterpClusterRootClientServiceBuilder(
      @Named(SearchRootModule.NAMED_EtwitterP_CLUSTER_SERVER_SETS_CONFIG)
          ServerSetsConfig serverSetsConfig,
      @Named(SearchRootModule.NAMED_SERVICE_INTERFACE) Class serviceIface,
      ResolverProtwittery resolverProtwittery,
      RemoteClientBuilder<EarlybirdService.ServiceIface> remoteClientBuilder) {
    return new RootClientServiceBuilder<>(
        serverSetsConfig, serviceIface, resolverProtwittery, remoteClientBuilder);
  }

  @Provides
  @Singleton
  MtlsServerSessionTrackerFilter<EarlybirdRequest, EarlybirdResponse>
  provideMtlsServerSessionTrackerFilter(StatsReceiver statsReceiver) {
    return new MtlsServerSessionTrackerFilter<>(statsReceiver);
  }
}
