package com.X.search.earlybird_root;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import scala.PartialFunction;

import com.google.inject.Provides;

import org.apache.thrift.protocol.TProtocolFactory;

import com.X.app.Flag;
import com.X.app.Flaggable;
import com.X.common.util.Clock;
import com.X.finagle.Service;
import com.X.finagle.mtls.authorization.server.MtlsServerSessionTrackerFilter;
import com.X.finagle.service.ReqRep;
import com.X.finagle.service.ResponseClass;
import com.X.finagle.stats.StatsReceiver;
import com.X.finagle.thrift.RichServerParam;
import com.X.finagle.thrift.ThriftClientRequest;
import com.X.inject.XModule;
import com.X.search.common.dark.DarkProxy;
import com.X.search.common.dark.ResolverProxy;
import com.X.search.common.partitioning.zookeeper.SearchZkClient;
import com.X.search.common.root.PartitionConfig;
import com.X.search.common.root.RemoteClientBuilder;
import com.X.search.common.root.RootClientServiceBuilder;
import com.X.search.common.root.SearchRootModule;
import com.X.search.common.root.ServerSetsConfig;
import com.X.search.common.util.zookeeper.ZooKeeperProxy;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;
import com.X.search.earlybird_root.filters.PreCacheRequestTypeCountFilter;
import com.X.search.earlybird_root.filters.QueryLangStatFilter;

/**
 * Provides common bindings.
 */
public class EarlybirdCommonModule extends XModule {
  static final String NAMED_ALT_CLIENT = "alt_client";
  static final String NAMED_EXP_CLUSTER_CLIENT = "exp_cluster_client";

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
      DarkProxy<ThriftClientRequest, byte[]> darkProxy,
      TProtocolFactory protocolFactory) {
    return darkProxy.toFilter().andThen(
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
  ZooKeeperProxy provideZookeeperClient() {
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
      ResolverProxy resolverProxy,
      RemoteClientBuilder<EarlybirdService.ServiceIface> remoteClientBuilder) {
    if (serverSetsConfig == null) {
      return null;
    }

    return new RootClientServiceBuilder<>(
        serverSetsConfig, serviceIface, resolverProxy, remoteClientBuilder);
  }

  @Provides
  @Singleton
  @Named(NAMED_EXP_CLUSTER_CLIENT)
  RootClientServiceBuilder<EarlybirdService.ServiceIface> provideExpClusterRootClientServiceBuilder(
      @Named(SearchRootModule.NAMED_EXP_CLUSTER_SERVER_SETS_CONFIG)
          ServerSetsConfig serverSetsConfig,
      @Named(SearchRootModule.NAMED_SERVICE_INTERFACE) Class serviceIface,
      ResolverProxy resolverProxy,
      RemoteClientBuilder<EarlybirdService.ServiceIface> remoteClientBuilder) {
    return new RootClientServiceBuilder<>(
        serverSetsConfig, serviceIface, resolverProxy, remoteClientBuilder);
  }

  @Provides
  @Singleton
  MtlsServerSessionTrackerFilter<EarlybirdRequest, EarlybirdResponse>
  provideMtlsServerSessionTrackerFilter(StatsReceiver statsReceiver) {
    return new MtlsServerSessionTrackerFilter<>(statsReceiver);
  }
}
